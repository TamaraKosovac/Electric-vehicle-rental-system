import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RentalPrice } from '../models/rental-price.model';

@Injectable({
  providedIn: 'root'
})
export class RentalPriceService {
  private apiUrl = 'http://localhost:8080/api/rental-prices';

  constructor(private http: HttpClient) {}

  getAll(): Observable<RentalPrice[]> {
    return this.http.get<RentalPrice[]>(this.apiUrl);
  }

  update(id: number, price: RentalPrice): Observable<RentalPrice> {
    return this.http.put<RentalPrice>(`${this.apiUrl}/${id}`, price);
  }
}