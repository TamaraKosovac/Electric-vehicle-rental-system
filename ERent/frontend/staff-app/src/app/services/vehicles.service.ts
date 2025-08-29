import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getCars(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/cars`);
  }

  getBikes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/bikes`);
  }

  getScooters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/scooters`);
  }

  deleteCar(id: number) {
    return this.http.delete(`${this.baseUrl}/cars/${id}`);
  }

  deleteBike(id: number) {
    return this.http.delete(`${this.baseUrl}/bikes/${id}`);
  }

  deleteScooter(id: number) {
    return this.http.delete(`${this.baseUrl}/scooters/${id}`);
  }

  createCar(car: any) {
  return this.http.post(`${this.baseUrl}/cars`, car);
  }

  createBike(bike: any) {
    return this.http.post(`${this.baseUrl}/bikes`, bike);
  }

  createScooter(scooter: any) {
    return this.http.post(`${this.baseUrl}/scooters`, scooter);
  }

  uploadCsv(file: File, type: string) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.baseUrl}/${type}/upload-csv`, formData);
  }
}
