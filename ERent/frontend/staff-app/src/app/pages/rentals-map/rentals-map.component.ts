import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';
import { RentalsService } from '../../services/rentals.service';
import { RentalDetails } from '../../models/rental-details.model';

@Component({
  selector: 'app-rentals-map',
  templateUrl: './rentals-map.component.html',
  styleUrls: ['./rentals-map.component.css']
})
export class RentalsMapComponent implements AfterViewInit, OnDestroy {
  private map!: L.Map;
  private resizeHandler = () => this.map.invalidateSize();

  constructor(private rentalsService: RentalsService) {}

  ngAfterViewInit(): void {
    this.initMap();
    setTimeout(() => {
      this.map.invalidateSize();
      this.loadRentals();
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
  }

  getImageUrl(path: string): string {
    return `http://localhost:8080${path}`;
  }
  
  private loadRentals(): void {
    this.rentalsService.getAll().subscribe((rentals: RentalDetails[]) => {
      rentals.forEach(r => {
        const lat = r.startLatitude || 44.7722;
        const lng = r.startLongitude || 17.1910;

        const marker = L.marker([lat, lng], { icon: this.getStatusIcon(r) }).addTo(this.map);

        marker.bindPopup(`
          <div style="min-width:260px; text-align:left; font-size:13px; line-height:1.4; color:#2e6f6a;">
            <div style="text-align:center; margin-bottom:8px;">
              <img src="${this.getImageUrl(r.imagePath)}" 
                  alt="${r.manufacturerName} ${r.vehicleModel}" 
                  style="width:160px; border-radius:8px; margin-bottom:6px;"/>
              <div style="font-weight:600; font-size:14px; margin-top:2px; color:#2e6f6a;">
                ${r.manufacturerName} ${r.vehicleModel}
              </div>
            </div>

            <div style="display:grid; grid-template-columns: 1fr 1fr; gap:6px 12px; align-items:center; color:#2e6f6a;">
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">person</span> 
                ${r.clientFirstName} ${r.clientLastName}
              </div>
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">euro</span> 
                ${r.price.toFixed(2)}
              </div>
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">location_on</span> 
                ${r.startLatitude}, ${r.startLongitude}
              </div>
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">flag</span> 
                ${r.endLatitude}, ${r.endLongitude}
              </div>
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">event</span> 
                ${r.startDateTime}
              </div>
              <div>
                <span class="material-icons" style="font-size:16px;vertical-align:middle; color:#2e6f6a;">event_busy</span> 
                ${r.endDateTime || 'Active'}
              </div>
            </div>
          </div>
        `);
        });
      });
    }

  private getStatusIcon(r: RentalDetails): L.DivIcon {
    let color = '#2e6f6a'; 

    if (r.endDateTime) {
      const now = new Date();
      const end = this.parseCustomDate(r.endDateTime);

      if (end && end <= now) {
        color = '#ff6b6b'; 
      }
    }

    return L.divIcon({
      html: `<span class="material-icons" style="color: ${color}; font-size: 36px;">location_on</span>`,
      className: '',
      iconSize: [36, 36],
      iconAnchor: [18, 36],
      popupAnchor: [0, -36]
    });
  }

  private parseCustomDate(dateStr?: string): Date | null {
    if (!dateStr) return null;

    const [datePart, timePart] = dateStr.split(' ');
    const [day, month, year] = datePart.split('-').map(Number);
    const [hour, minute] = timePart.split(':').map(Number);

    return new Date(year, month - 1, day, hour, minute);
  }
}
