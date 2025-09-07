import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Manufacturer } from '../models/manufacturer.model'; 

@Injectable({
  providedIn: 'root'
})
export class ManufacturersService {
  private baseUrl = 'http://localhost:8080/api/manufacturers';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Manufacturer[]> {
    return this.http.get<Manufacturer[]>(this.baseUrl);
  }

  getById(id: number): Observable<Manufacturer> {
    return this.http.get<Manufacturer>(`${this.baseUrl}/${id}`);
  }

  create(manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.post<Manufacturer>(this.baseUrl, manufacturer);
  }

  update(id: number, manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.put<Manufacturer>(`${this.baseUrl}/${id}`, manufacturer);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
