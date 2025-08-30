// src/app/services/vehicles.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // --- LISTS ---
  getCars(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/cars`);
  }
  getBikes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/bikes`);
  }
  getScooters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/scooters`);
  }

  // --- CRUD CARS ---
  deleteCar(id: number) {
    return this.http.delete(`${this.baseUrl}/cars/${id}`);
  }
  createCar(car: any) {
    return this.http.post(`${this.baseUrl}/cars`, car);
  }

  // --- CRUD BIKES ---
  deleteBike(id: number) {
    return this.http.delete(`${this.baseUrl}/bikes/${id}`);
  }
  createBike(bike: any) {
    return this.http.post(`${this.baseUrl}/bikes`, bike);
  }

  // --- CRUD SCOOTERS ---
  deleteScooter(id: number) {
    return this.http.delete(`${this.baseUrl}/scooters/${id}`);
  }
  createScooter(scooter: any) {
    return this.http.post(`${this.baseUrl}/scooters`, scooter);
  }

  // --- CSV UPLOAD ---
  uploadCsv(file: File, type: string) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.baseUrl}/${type}/upload-csv`, formData);
  }

  // --- GENERIC DETAILS ---
  getVehicleById(type: string, id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${type}/${id}`);
  }

  getMalfunctions(type: string, vehicleId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${type}/${vehicleId}/malfunctions`);
  }
  addMalfunction(type: string, vehicleId: number, malfunction: any) {
    return this.http.post(`${this.baseUrl}/${type}/${vehicleId}/malfunctions`, malfunction);
  }
  deleteMalfunction(type: string, id: number) {
    return this.http.delete(`${this.baseUrl}/${type}/malfunctions/${id}`);
  }

  getRentals(type: string, vehicleId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${type}/${vehicleId}/rentals`);
  }
}
