import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getClients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/clients`);
  }

  blockClient(id: number) {
    return this.http.put(`${this.baseUrl}/clients/${id}/block`, {});
  }

  unblockClient(id: number) {
    return this.http.put(`${this.baseUrl}/clients/${id}/unblock`, {});
  }

  getEmployees(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/employees`);
  }

  createEmployee(employee: any) {
    return this.http.post(`${this.baseUrl}/employees`, employee);
  }

  updateEmployee(id: number, employee: any) {
    return this.http.put(`${this.baseUrl}/employees/${id}`, employee);
  }

  deleteEmployee(id: number) {
    return this.http.delete(`${this.baseUrl}/employees/${id}`);
  }

  activateClient(id: number) {
    return this.http.put(`${this.baseUrl}/clients/${id}/activate`, {});
  }

  deactivateClient(id: number) {
    return this.http.put(`${this.baseUrl}/clients/${id}/deactivate`, {});
  }
}