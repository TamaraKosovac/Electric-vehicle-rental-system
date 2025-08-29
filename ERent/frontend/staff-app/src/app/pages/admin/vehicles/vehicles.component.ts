import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';
import { VehiclesService } from '../../../services/vehicles.service';

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
    HttpClientModule
  ],
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {
  cars: any[] = [];
  bikes: any[] = [];
  scooters: any[] = [];
  displayedColumns: string[] = ['id', 'model', 'actions'];

  // objekti za dodavanje novih vozila
  newCar: any = { model: '', manufacturer: '', price: 0 };
  newBike: any = { model: '', manufacturer: '', price: 0 };
  newScooter: any = { model: '', manufacturer: '', price: 0 };

  // fajlovi za CSV upload
  selectedFiles: { [key: string]: File | null } = { cars: null, bikes: null, scooters: null };

  constructor(private vehiclesService: VehiclesService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.vehiclesService.getCars().subscribe(data => this.cars = data);
    this.vehiclesService.getBikes().subscribe(data => this.bikes = data);
    this.vehiclesService.getScooters().subscribe(data => this.scooters = data);
  }

  // DELETE
  deleteCar(id: number) {
    this.vehiclesService.deleteCar(id).subscribe(() => {
      this.cars = this.cars.filter(c => c.id !== id);
    });
  }

  deleteBike(id: number) {
    this.vehiclesService.deleteBike(id).subscribe(() => {
      this.bikes = this.bikes.filter(b => b.id !== id);
    });
  }

  deleteScooter(id: number) {
    this.vehiclesService.deleteScooter(id).subscribe(() => {
      this.scooters = this.scooters.filter(s => s.id !== id);
    });
  }

  // CREATE
  addCar() {
    this.vehiclesService.createCar(this.newCar).subscribe(() => {
      this.loadData();
      this.newCar = { model: '', manufacturer: '', price: 0 };
    });
  }

  addBike() {
    this.vehiclesService.createBike(this.newBike).subscribe(() => {
      this.loadData();
      this.newBike = { model: '', manufacturer: '', price: 0 };
    });
  }

  addScooter() {
    this.vehiclesService.createScooter(this.newScooter).subscribe(() => {
      this.loadData();
      this.newScooter = { model: '', manufacturer: '', price: 0 };
    });
  }

  // CSV UPLOAD
  onFileSelected(event: any, type: string) {
    this.selectedFiles[type] = event.target.files[0];
  }

  uploadCSV(type: string) {
    if (this.selectedFiles[type]) {
      this.vehiclesService.uploadCsv(this.selectedFiles[type]!, type).subscribe(() => {
        this.loadData();
        this.selectedFiles[type] = null;
      });
    }
  }
}
