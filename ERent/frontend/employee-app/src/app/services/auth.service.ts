import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/login-response.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login';
  private userKey = 'user';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, { username, password });
  }

  setUser(user: LoginResponse): void {
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  getUser(): LoginResponse | null {
    const data = localStorage.getItem(this.userKey);
    return data ? (JSON.parse(data) as LoginResponse) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getUser()?.token; 
  }

  logout(): void {
    localStorage.removeItem(this.userKey);
  }
}