import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from '../models/car.model';
import { Bike } from '../models/bike.model';
import { Scooter } from '../models/scooter.model';
import { Malfunction } from '../models/malfunction.model';

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.baseUrl}/cars`);
  }

  getBikes(): Observable<Bike[]> {
    return this.http.get<Bike[]>(`${this.baseUrl}/bikes`);
  }

  getScooters(): Observable<Scooter[]> {
    return this.http.get<Scooter[]>(`${this.baseUrl}/scooters`);
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/cars/${id}`);
  }

  createCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.baseUrl}/cars`, car);
  }

  deleteBike(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/bikes/${id}`);
  }

  createBike(bike: Bike): Observable<Bike> {
    return this.http.post<Bike>(`${this.baseUrl}/bikes`, bike);
  }

  deleteScooter(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/scooters/${id}`);
  }

  createScooter(scooter: Scooter): Observable<Scooter> {
    return this.http.post<Scooter>(`${this.baseUrl}/scooters`, scooter);
  }

  uploadCsv(file: File, type: string): Observable<void> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<void>(`${this.baseUrl}/${type}/upload-csv`, formData);
  }

  getVehicleById(type: 'cars' | 'bikes' | 'scooters', id: number): Observable<Car | Bike | Scooter> {
    return this.http.get<Car | Bike | Scooter>(`${this.baseUrl}/${type}/${id}`);
  }

  getMalfunctions(type: 'cars' | 'bikes' | 'scooters', vehicleId: number): Observable<Malfunction[]> {
    return this.http.get<Malfunction[]>(`${this.baseUrl}/${type}/${vehicleId}/malfunctions`);
  }

  addMalfunction(type: 'cars' | 'bikes' | 'scooters', vehicleId: number, malfunction: Malfunction): Observable<Malfunction> {
    return this.http.post<Malfunction>(`${this.baseUrl}/${type}/${vehicleId}/malfunctions`, malfunction);
  }

  deleteMalfunction(type: 'cars' | 'bikes' | 'scooters', id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${type}/malfunctions/${id}`);
  }

  getRentals(type: 'cars' | 'bikes' | 'scooters', vehicleId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${type}/${vehicleId}/rentals`);
  }
}
