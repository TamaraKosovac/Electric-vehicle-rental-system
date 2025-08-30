import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManufacturersService {
  private baseUrl = 'http://localhost:8080/api/manufacturers';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  create(manufacturer: any) {
    return this.http.post(this.baseUrl, manufacturer);
  }

  update(id: number, manufacturer: any) {
    return this.http.put(`${this.baseUrl}/${id}`, manufacturer);
  }

  delete(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
