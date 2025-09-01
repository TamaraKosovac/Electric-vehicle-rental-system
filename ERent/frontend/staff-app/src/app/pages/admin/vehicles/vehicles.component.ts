import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';

import { VehiclesService } from '../../../services/vehicles.service';
import { Car } from '../../../models/car.model';
import { Bike } from '../../../models/bike.model';
import { Scooter } from '../../../models/scooter.model';
import { MinimalPaginatorComponent } from '../../../shared/minimal-paginator/minimal-paginator.component';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';


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
    DataTableComponent 
  ],
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit, AfterViewInit {
  carsDS = new MatTableDataSource<Car>([]);
  bikesDS = new MatTableDataSource<Bike>([]);
  scootersDS = new MatTableDataSource<Scooter>([]);

  @ViewChild('paginatorCars') paginatorCars!: MatPaginator;
  @ViewChild('paginatorBikes') paginatorBikes!: MatPaginator;
  @ViewChild('paginatorScooters') paginatorScooters!: MatPaginator;

  currentCarPage = 1;
  carTotalPages = 1;

  currentBikePage = 1;
  bikeTotalPages = 1;

  currentScooterPage = 1;
  scooterTotalPages = 1;

  selectedFiles: { [key: string]: File | null } = {
    cars: null,
    bikes: null,
    scooters: null
  };

  activeTab: 'cars' | 'bikes' | 'scooters' = 'cars';
  activeTabIndex = 0;

  constructor(private vehiclesService: VehiclesService) {}

  ngOnInit() {
    this.loadData();

    this.carsDS.filterPredicate = (data, filter) =>
      (data.model ?? '').toLowerCase().includes(filter);

    this.bikesDS.filterPredicate = (data, filter) =>
      (data.model ?? '').toLowerCase().includes(filter);

    this.scootersDS.filterPredicate = (data, filter) =>
      (data.model ?? '').toLowerCase().includes(filter);
  }

  ngAfterViewInit() {
    this.setupPaginator(this.carsDS, this.paginatorCars, 'car');
    this.setupPaginator(this.bikesDS, this.paginatorBikes, 'bike');
    this.setupPaginator(this.scootersDS, this.paginatorScooters, 'scooter');
  }

  private setupPaginator(ds: MatTableDataSource<any>, paginator: MatPaginator, type: 'car' | 'bike' | 'scooter') {
    ds.paginator = paginator;
    paginator.page.subscribe(() => {
      if (type === 'car') {
        this.currentCarPage = paginator.pageIndex + 1;
        this.carTotalPages = Math.ceil(ds.data.length / paginator.pageSize);
      }
      if (type === 'bike') {
        this.currentBikePage = paginator.pageIndex + 1;
        this.bikeTotalPages = Math.ceil(ds.data.length / paginator.pageSize);
      }
      if (type === 'scooter') {
        this.currentScooterPage = paginator.pageIndex + 1;
        this.scooterTotalPages = Math.ceil(ds.data.length / paginator.pageSize);
      }
    });
  }

  loadData() {
    this.vehiclesService.getCars().subscribe(data => (this.carsDS.data = data));
    this.vehiclesService.getBikes().subscribe(data => (this.bikesDS.data = data));
    this.vehiclesService.getScooters().subscribe(data => (this.scootersDS.data = data));
  }

  onTabChange(event: any) {
    if (event.index === 0) this.activeTab = 'cars';
    if (event.index === 1) this.activeTab = 'bikes';
    if (event.index === 2) this.activeTab = 'scooters';
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();
    if (this.activeTab === 'cars') this.carsDS.filter = value;
    if (this.activeTab === 'bikes') this.bikesDS.filter = value;
    if (this.activeTab === 'scooters') this.scootersDS.filter = value;
  }

  deleteCar(id: number) {
    this.vehiclesService.deleteCar(id).subscribe(() => {
      this.carsDS.data = this.carsDS.data.filter(c => c.id !== id);
    });
  }

  deleteBike(id: number) {
    this.vehiclesService.deleteBike(id).subscribe(() => {
      this.bikesDS.data = this.bikesDS.data.filter(b => b.id !== id);
    });
  }

  deleteScooter(id: number) {
    this.vehiclesService.deleteScooter(id).subscribe(() => {
      this.scootersDS.data = this.scootersDS.data.filter(s => s.id !== id);
    });
  }

  startCreate(type: 'cars' | 'bikes' | 'scooters') {
    console.log('Start create for', type);
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
  console.log('Edit car', car);
}

onEditBike(bike: Bike) {
  console.log('Edit bike', bike);
}

onEditScooter(scooter: Scooter) {
  console.log('Edit scooter', scooter);
}
}
