import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { VehiclesService } from '../../../services/vehicles.service';

import { CarDetails } from '../../../models/car-details.model';
import { BikeDetails } from '../../../models/bike-details.model';
import { ScooterDetails } from '../../../models/scooter-details.model';
import { Malfunction } from '../../../models/malfunction.model';
import { MatIconModule } from '@angular/material/icon';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';   
import { MatInputModule } from '@angular/material/input';  
import { MatDialog } from '@angular/material/dialog';
import { MalfunctionFormComponent } from './malfunction-form/malfunction-form.component';

@Component({
  selector: 'app-vehicle-details',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    DataTableComponent,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {
  vehicleType!: 'cars' | 'bikes' | 'scooters';
  vehicleId!: number;

  vehicle!: CarDetails | BikeDetails | ScooterDetails;

  malfunctions: Malfunction[] = [];
  rentals: any[] = []; 

  activeTabIndex = 0;

  constructor(
    private route: ActivatedRoute,
    private vehiclesService: VehiclesService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.vehicleType = this.route.snapshot.paramMap.get('type') as 'cars' | 'bikes' | 'scooters';
    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));

    this.vehiclesService.getVehicleById(this.vehicleType, this.vehicleId)
      .subscribe(v => {
        this.vehicle = v;
        this.malfunctions = v.malfunctions || []; 
      });

    this.vehiclesService.getRentals(this.vehicleType, this.vehicleId)
      .subscribe(r => this.rentals = r);
  }

  onTabChange(event: any) {
    this.activeTabIndex = event.index;
  }

  openAddMalfunctionDialog() {
    const dialogRef = this.dialog.open(MalfunctionFormComponent, {
      width: '500px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        result.vehicleId = this.vehicleId;
        this.vehiclesService
          .addMalfunction(this.vehicleType, this.vehicleId, result)
          .subscribe(newM => this.malfunctions.push(newM));
      }
    });
  }

  onEditMalfunction(malfunction: Malfunction) {
    const newDesc = prompt('Edit malfunction description:', malfunction.description);
    if (newDesc && newDesc.trim()) {
      malfunction.description = newDesc.trim();
    }
  }

  deleteMalfunction(id: number) {
    this.vehiclesService.deleteMalfunction(this.vehicleType, id).subscribe(() => {
      this.malfunctions = this.malfunctions.filter(m => m.id !== id);
    });
  }

  get car(): CarDetails | null {
    return this.vehicleType === 'cars' ? (this.vehicle as CarDetails) : null;
  }

  get bike(): BikeDetails | null {
    return this.vehicleType === 'bikes' ? (this.vehicle as BikeDetails) : null;
  }

  get scooter(): ScooterDetails | null {
    return this.vehicleType === 'scooters' ? (this.vehicle as ScooterDetails) : null;
  }

  getImageUrl(path: string): string {
    return `http://localhost:8080${path}`;
  }

  applyMalfunctionFilter(event: KeyboardEvent) {
    const value = (event.target as HTMLInputElement).value.toLowerCase().trim();
    this.malfunctions = (this.vehicle.malfunctions || [])
      .filter(m => m.description.toLowerCase().includes(value));
  }

  applyRentalFilter(event: KeyboardEvent) {
    const value = (event.target as HTMLInputElement).value.toLowerCase().trim();
    this.rentals = this.rentals.filter(r =>
      r.price.toString().includes(value) ||
      r.startDateTime.toLowerCase().includes(value) ||
      r.endDateTime.toLowerCase().includes(value)
    );
  }

  onSearch(event: KeyboardEvent) {
    if (this.activeTabIndex === 0) {
      this.applyMalfunctionFilter(event);
    } else {
      this.applyRentalFilter(event);
    }
  }
}
