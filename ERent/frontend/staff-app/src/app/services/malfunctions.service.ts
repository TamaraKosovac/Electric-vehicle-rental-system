import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Malfunction } from '../models/malfunction.model';

@Injectable({
  providedIn: 'root'
})
export class MalfunctionsService {
  private apiUrl = 'http://localhost:8080/api/malfunctions';

  constructor(private http: HttpClient) {}

  getById(id: number): Observable<Malfunction> {
    return this.http.get<Malfunction>(`${this.apiUrl}/${id}`);
  }

  create(malfunction: Malfunction): Observable<Malfunction> {
    return this.http.post<Malfunction>(this.apiUrl, malfunction);
  }

  update(id: number, malfunction: Malfunction): Observable<Malfunction> {
    return this.http.put<Malfunction>(`${this.apiUrl}/${id}`, malfunction);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAll(): Observable<Malfunction[]> {
    return this.http.get<Malfunction[]>(this.apiUrl);
  }

  getMalfunctionsByVehicleType(): Observable<{ label: string, value: number }[]> {
    return this.http.get<{ label: string, value: number }[]>(`${this.apiUrl}/by-vehicle-type`);
  }
}