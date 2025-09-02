import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

import { VehiclesService } from '../../../services/vehicles.service';
import { Car } from '../../../models/car.model';
import { Bike } from '../../../models/bike.model';
import { Scooter } from '../../../models/scooter.model';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { VehicleFormComponent } from './vehicle-form/vehicle-form.component';
import { MinimalPaginatorComponent } from '../../../shared/minimal-paginator/minimal-paginator.component';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTabsModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    HttpClientModule,
    DataTableComponent,
    MatDialogModule,
    MatSnackBarModule,
    MinimalPaginatorComponent,
    RouterModule
  ],
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {
  allCars: Car[] = [];
  allBikes: Bike[] = [];
  allScooters: Scooter[] = [];

  carsDS = new MatTableDataSource<Car>([]);
  bikesDS = new MatTableDataSource<Bike>([]);
  scootersDS = new MatTableDataSource<Scooter>([]);

  currentCarPage = 1;
  carTotalPages = 1;

  currentBikePage = 1;
  bikeTotalPages = 1;

  currentScooterPage = 1;
  scooterTotalPages = 1;

  pageSize = 10;

  selectedFiles: { [key: string]: File | null } = {
    cars: null,
    bikes: null,
    scooters: null
  };

  activeTab: 'cars' | 'bikes' | 'scooters' = 'cars';
  activeTabIndex = 0;

  constructor(
    private vehiclesService: VehiclesService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadData();
  }

  private updateCarPage() {
    const start = (this.currentCarPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.carsDS.data = this.allCars.slice(start, end);
    this.carTotalPages = Math.ceil(this.allCars.length / this.pageSize) || 1;
  }

  previousCarPage() {
    if (this.currentCarPage > 1) {
      this.currentCarPage--;
      this.updateCarPage();
    }
  }

  nextCarPage() {
    if (this.currentCarPage < this.carTotalPages) {
      this.currentCarPage++;
      this.updateCarPage();
    }
  }

  private updateBikePage() {
    const start = (this.currentBikePage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.bikesDS.data = this.allBikes.slice(start, end);
    this.bikeTotalPages = Math.ceil(this.allBikes.length / this.pageSize) || 1;
  }

  previousBikePage() {
    if (this.currentBikePage > 1) {
      this.currentBikePage--;
      this.updateBikePage();
    }
  }

  nextBikePage() {
    if (this.currentBikePage < this.bikeTotalPages) {
      this.currentBikePage++;
      this.updateBikePage();
    }
  }

  private updateScooterPage() {
    const start = (this.currentScooterPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.scootersDS.data = this.allScooters.slice(start, end);
    this.scooterTotalPages = Math.ceil(this.allScooters.length / this.pageSize) || 1;
  }

  previousScooterPage() {
    if (this.currentScooterPage > 1) {
      this.currentScooterPage--;
      this.updateScooterPage();
    }
  }

  nextScooterPage() {
    if (this.currentScooterPage < this.scooterTotalPages) {
      this.currentScooterPage++;
      this.updateScooterPage();
    }
  }

  loadData() {
    this.vehiclesService.getCars().subscribe(data => {
      this.allCars = data;
      this.currentCarPage = 1;
      this.updateCarPage();
    });

    this.vehiclesService.getBikes().subscribe(data => {
      this.allBikes = data;
      this.currentBikePage = 1;
      this.updateBikePage();
    });

    this.vehiclesService.getScooters().subscribe(data => {
      this.allScooters = data;
      this.currentScooterPage = 1;
      this.updateScooterPage();
    });
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();

    if (this.activeTab === 'cars') {
      this.carsDS.data = this.allCars
        .filter(c => (c.model ?? '').toLowerCase().includes(value))
        .slice(0, this.pageSize);
    }
    if (this.activeTab === 'bikes') {
      this.bikesDS.data = this.allBikes
        .filter(b => (b.model ?? '').toLowerCase().includes(value))
        .slice(0, this.pageSize);
    }
    if (this.activeTab === 'scooters') {
      this.scootersDS.data = this.allScooters
        .filter(s => (s.model ?? '').toLowerCase().includes(value))
        .slice(0, this.pageSize);
    }
  }

  deleteCar(id: number) {
    this.vehiclesService.deleteCar(id).subscribe(() => {
      this.allCars = this.allCars.filter(c => c.id !== id);
      this.updateCarPage();
      this.snackBar.open('Car deleted successfully!', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-success']
      });
    }, () => {
      this.snackBar.open('Failed to delete car.', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-error']
      });
    });
  }

  deleteBike(id: number) {
    this.vehiclesService.deleteBike(id).subscribe(() => {
      this.allBikes = this.allBikes.filter(b => b.id !== id);
      this.updateBikePage();
      this.snackBar.open('Bike deleted successfully!', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-success']
      });
    }, () => {
      this.snackBar.open('Failed to delete bike.', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-error']
      });
    });
  }

  deleteScooter(id: number) {
    this.vehiclesService.deleteScooter(id).subscribe(() => {
      this.allScooters = this.allScooters.filter(s => s.id !== id);
      this.updateScooterPage();
      this.snackBar.open('Scooter deleted successfully!', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-success']
      });
    }, () => {
      this.snackBar.open('Failed to delete scooter.', '', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
        panelClass: ['snackbar-error']
      });
    });
  }


  startCreate(type: 'cars' | 'bikes' | 'scooters') {
    const dialogRef = this.dialog.open(VehicleFormComponent, {
      width: '500px',
      data: { type }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const { vehicle, image } = result;

        let create$: Observable<Car | Bike | Scooter>;
        if (type === 'cars') {
          create$ = this.vehiclesService.createCar(vehicle, image);
        } else if (type === 'bikes') {
          create$ = this.vehiclesService.createBike(vehicle, image);
        } else {
          create$ = this.vehiclesService.createScooter(vehicle, image);
        }

        create$.subscribe({
          next: (createdVehicle) => {
            (createdVehicle as any).hasMalfunctions = false;

            if (type === 'cars') {
              this.allCars.push(createdVehicle as Car);
              this.updateCarPage();
            } else if (type === 'bikes') {
              this.allBikes.push(createdVehicle as Bike);
              this.updateBikePage();
            } else {
              this.allScooters.push(createdVehicle as Scooter);
              this.updateScooterPage();
            }

            this.snackBar.open(`${type.slice(0, -1)} created successfully!`, '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open(`Failed to create ${type.slice(0, -1)}.`, '', {
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

  onFileSelected(event: any, type: 'cars' | 'bikes' | 'scooters') {
    this.selectedFiles[type] = event.target.files[0] ?? null;
  }

  uploadCSV(type: 'cars' | 'bikes' | 'scooters') {
    const file = this.selectedFiles[type];
    if (!file) return;
    this.vehiclesService.uploadCsv(file, type).subscribe(() => {
      this.loadData();
      this.selectedFiles[type] = null;
    });
  }

  onEditCar(car: Car) {
    this.vehiclesService.getCarById(car.id).subscribe(fetchedCar => {
      const dialogRef = this.dialog.open(VehicleFormComponent, {
        width: '500px',
        data: { type: 'cars', vehicle: fetchedCar }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const { vehicle, image } = result as { vehicle: Car; image?: File };
          this.vehiclesService.updateCar(car.id, vehicle, image).subscribe(updated => {
            const idx = this.allCars.findIndex(c => c.id === updated.id);
            if (idx !== -1) this.allCars[idx] = updated;
            this.updateCarPage();
            this.snackBar.open('Car updated successfully!', '', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
            panelClass: ['snackbar-success']
          });
          });
        }
      });
    });
  }

  onEditBike(bike: Bike) {
    this.vehiclesService.getBikeById(bike.id).subscribe(fetchedBike => {
      const dialogRef = this.dialog.open(VehicleFormComponent, {
        width: '500px',
        data: { type: 'bikes', vehicle: fetchedBike }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const { vehicle, image } = result as { vehicle: Bike; image?: File };
          this.vehiclesService.updateBike(bike.id, vehicle, image).subscribe(updated => {
            const idx = this.allBikes.findIndex(b => b.id === updated.id);
            if (idx !== -1) this.allBikes[idx] = updated;
            this.updateBikePage();
            this.snackBar.open('Bike updated successfully!', '', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
            panelClass: ['snackbar-success']
          });
          });
        }
      });
    });
  }

  onEditScooter(scooter: Scooter) {
    this.vehiclesService.getScooterById(scooter.id).subscribe(fetchedScooter => {
      const dialogRef = this.dialog.open(VehicleFormComponent, {
        width: '500px',
        data: { type: 'scooters', vehicle: fetchedScooter }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const { vehicle, image } = result as { vehicle: Scooter; image?: File };
          this.vehiclesService.updateScooter(scooter.id, vehicle, image).subscribe(updated => {
            const idx = this.allScooters.findIndex(s => s.id === updated.id);
            if (idx !== -1) this.allScooters[idx] = updated;
            this.updateScooterPage();
            this.snackBar.open('Scooter updated successfully!', '', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top',
            panelClass: ['snackbar-success']
          });
          });
        }
      });
    });
  }

  onTabChange(event: any) {
    if (event.index === 0) this.activeTab = 'cars';
    if (event.index === 1) this.activeTab = 'bikes';
    if (event.index === 2) this.activeTab = 'scooters';
  }

  onInfo(type: 'cars' | 'bikes' | 'scooters', id: number) {
    this.router.navigate(['/admin/vehicles', type, id]);
  }
}
