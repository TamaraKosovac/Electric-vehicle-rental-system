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

  private loadRentals(): void {
    this.rentalsService.getAll().subscribe((rentals: RentalDetails[]) => {
      rentals.forEach(r => {
        const lat = r.startLatitude || 44.7722;
        const lng = r.startLongitude || 17.1910;

        const marker = L.marker([lat, lng]).addTo(this.map);
        marker.bindPopup(`
          <b>${r.manufacturerName} ${r.vehicleModel}</b><br>
          Client: ${r.client}<br>
          Start: ${r.startDateTime}<br>
          End: ${r.endDateTime}<br>
          Price: ${r.price} KM
        `);
      });
    });
  }
}
