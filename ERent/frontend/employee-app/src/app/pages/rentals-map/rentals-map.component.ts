import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';
import 'leaflet.markercluster';   
import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from '../../models/vehicle.model';
import { VehicleState } from '../../models/enums/vehicle-state.enum';

@Component({
  selector: 'app-rentals-map',
  templateUrl: './rentals-map.component.html',
  styleUrls: ['./rentals-map.component.css']
})
export class RentalsMapComponent implements AfterViewInit, OnDestroy {
  private map!: L.Map;
  private markerCluster!: L.MarkerClusterGroup;
  private resizeHandler = () => this.map.invalidateSize();

  constructor(private vehicleService: VehicleService) {}

  ngAfterViewInit(): void {
    this.initMap();
    setTimeout(() => {
      this.map.invalidateSize();
      this.loadVehicles();
    }, 300);

    window.addEventListener('resize', this.resizeHandler);
  }

  ngOnDestroy(): void {
    window.removeEventListener('resize', this.resizeHandler);
    if (this.map) {
      this.map.remove();
    }
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [44.7722, 17.1910],
      zoom: 13
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors',
      detectRetina: true,
      maxZoom: 19,
      tileSize: 256
    }).addTo(this.map);

    this.markerCluster = L.markerClusterGroup();
    this.map.addLayer(this.markerCluster);
  }

  getImageUrl(path: string): string {
    return `http://localhost:8080/images${path}`;
  }

  private loadVehicles(): void {
    this.vehicleService.getAllVehicles().subscribe((vehicles: Vehicle[]) => {
      vehicles.forEach(v => {
        const lat = v.currentLatitude || 44.7722;
        const lng = v.currentLongitude || 17.1910;

        let route = '';
        const prefix = v.uniqueId.toLowerCase();
        if (prefix.startsWith('car')) {
          route = 'cars';
        } else if (prefix.startsWith('bike')) {
          route = 'bikes';
        } else if (prefix.startsWith('scooter')) {
          route = 'scooters';
        }

        const detailsUrl = `http://localhost:4200/dashboard/vehicles/${route}/${v.id}`;

        const marker = L.marker([lat, lng], { icon: this.getStatusIcon(v) });

        marker.bindPopup(`
          <div style="min-width:260px; text-align:left; font-size:13px; line-height:1.4; color:#2e6f6a;">
            <div style="text-align:center; margin-bottom:8px;">
              <a href="${detailsUrl}">
                <img src="${this.getImageUrl(v.imagePath)}" 
                    alt="${v.manufacturer} ${v.model}" 
                    style="width:160px; border-radius:8px; margin-bottom:6px; cursor:pointer;"/>
              </a>
              <div style="font-weight:600; font-size:14px; margin-top:2px; color:#2e6f6a;">
                ${v.manufacturer} ${v.model}
              </div>
              <div style="margin-top:4px; font-size:12px;">
                State: ${v.state}
              </div>
            </div>
          </div>
        `);

        this.markerCluster.addLayer(marker);
      });
    });
  }

  private getStatusIcon(v: Vehicle): L.DivIcon {
    let color = '#2e6f6a'; 

    if (v.state === VehicleState.RENTED) {
      color = '#ff6b6b'; 
    } else if (v.state === VehicleState.BROKEN) {
      color = '#fb8c00'; 
    }

    return L.divIcon({
      html: `<span class="material-icons" style="color: ${color}; font-size: 36px;">location_on</span>`,
      className: '',
      iconSize: [36, 36],
      iconAnchor: [18, 36],
      popupAnchor: [0, -36]
    });
  }
}
