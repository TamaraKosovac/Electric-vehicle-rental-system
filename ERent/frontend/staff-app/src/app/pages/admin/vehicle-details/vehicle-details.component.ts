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
import { MinimalPaginatorComponent } from '../../../shared/minimal-paginator/minimal-paginator.component';
import { MalfunctionsService } from '../../../services/malfunctions.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RentalsService } from '../../../services/rentals.service';
import { RentalDetails } from '../../../models/rental-details.model';


@Component({
  selector: 'app-vehicle-details',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    DataTableComponent,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MinimalPaginatorComponent,
    MatSnackBarModule   
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

  pageSize = 5;
  currentMalfunctionPage = 1;
  malfunctionTotalPages = 1;

  currentRentalPage = 1;
  rentalTotalPages = 1;

  activeTabIndex = 0;

  constructor(
    private route: ActivatedRoute,
    private vehiclesService: VehiclesService,
    private malfunctionsService: MalfunctionsService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private rentalsService: RentalsService, 
  ) {}

  ngOnInit(): void {
    this.vehicleType = this.route.snapshot.paramMap.get('type') as 'cars' | 'bikes' | 'scooters';
    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));

    this.vehiclesService.getVehicleById(this.vehicleType, this.vehicleId)
      .subscribe(v => {
        this.vehicle = v;
        this.malfunctions = v.malfunctions || []; 
        this.updateMalfunctionPage();
      });

      this.rentalsService.getRentalsByVehicleId(this.vehicleId)
        .subscribe(r => {
          this.rentals = r.map(item => ({
            ...item,
            client: item.clientFirstName + ' ' + item.clientLastName
          }));
          this.updateRentalPage();
        });
  }

  onTabChange(event: any) {
    this.activeTabIndex = event.index;
  }

  private updateMalfunctionPage() {
    this.malfunctionTotalPages = Math.ceil(this.malfunctions.length / this.pageSize) || 1;
  }

  previousMalfunctionPage() {
    if (this.currentMalfunctionPage > 1) {
      this.currentMalfunctionPage--;
      this.updateMalfunctionPage();
    }
  }

  nextMalfunctionPage() {
    if (this.currentMalfunctionPage < this.malfunctionTotalPages) {
      this.currentMalfunctionPage++;
      this.updateMalfunctionPage();
    }
  }

  private updateRentalPage() {
    this.rentalTotalPages = Math.ceil(this.rentals.length / this.pageSize) || 1;
  }

  previousRentalPage() {
    if (this.currentRentalPage > 1) {
      this.currentRentalPage--;
      this.updateRentalPage();
    }
  }

  nextRentalPage() {
    if (this.currentRentalPage < this.rentalTotalPages) {
      this.currentRentalPage++;
      this.updateRentalPage();
    }
  }

  openAddMalfunctionDialog() {
    const dialogRef = this.dialog.open(MalfunctionFormComponent, {
      width: '500px',
      data: { vehicleId: this.vehicleId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.malfunctionsService
          .create(result)
          .subscribe({
            next: (newM) => {
              this.malfunctions.push(newM);
              this.updateMalfunctionPage();

              this.snackBar.open('Malfunction added successfully!', '', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
                panelClass: ['snackbar-success']
              });
            },
            error: () => {
              this.snackBar.open('Failed to add malfunction.', '', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
                panelClass: ['snackbar-error']
              });
            }
          });
      }
    });
  }

  deleteMalfunction(id: number) {
    this.malfunctionsService.delete(id).subscribe({
      next: () => {
        this.malfunctions = this.malfunctions.filter(m => m.id !== id);
        this.updateMalfunctionPage();

        this.snackBar.open('Malfunction deleted successfully!', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-success']
        });
      },
      error: () => {
        this.snackBar.open('Failed to delete malfunction.', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  onEditMalfunction(malfunction: Malfunction) {
    const dialogRef = this.dialog.open(MalfunctionFormComponent, {
      width: '500px',
      data: { malfunction }   
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.malfunctionsService
          .update(malfunction.id, result)   
          .subscribe({
            next: (updatedM) => {
              const idx = this.malfunctions.findIndex(m => m.id === updatedM.id);
              if (idx !== -1) this.malfunctions[idx] = updatedM;

              this.updateMalfunctionPage();

              this.snackBar.open('Malfunction updated successfully!', '', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
                panelClass: ['snackbar-success']
              });
            },
            error: () => {
              this.snackBar.open('Failed to update malfunction.', '', {
                duration: 3000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
                panelClass: ['snackbar-error']
              });
            }
          });
      }
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
    this.currentMalfunctionPage = 1;
    this.updateMalfunctionPage();
  }

  applyRentalFilter(event: KeyboardEvent) {
    const value = (event.target as HTMLInputElement).value.toLowerCase().trim();
    this.rentals = this.rentals.filter(r =>
      r.price.toString().includes(value) ||
      r.startDateTime.toLowerCase().includes(value) ||
      r.endDateTime.toLowerCase().includes(value)
    );
    this.currentRentalPage = 1;
    this.updateRentalPage();
  }

  onSearch(event: KeyboardEvent) {
    if (this.activeTabIndex === 0) {
      this.applyMalfunctionFilter(event);
    } else {
      this.applyRentalFilter(event);
    }
  }
}
