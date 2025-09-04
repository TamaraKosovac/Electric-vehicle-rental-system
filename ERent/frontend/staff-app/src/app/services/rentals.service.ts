import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RentalDetails } from '../models/rental-details.model';

@Injectable({
  providedIn: 'root'
})
export class RentalsService {

  private baseUrl = 'http://localhost:8080/api/rentals';

  constructor(private http: HttpClient) { }

  getRentalsByVehicleId(vehicleId: number): Observable<RentalDetails[]> {
    return this.http.get<RentalDetails[]>(`${this.baseUrl}/vehicle/${vehicleId}`);
  }

    getAll(): Observable<RentalDetails[]> {
    return this.http.get<RentalDetails[]>(this.baseUrl);
  }
}
