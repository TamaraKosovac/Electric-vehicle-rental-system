import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  username: string;
  role: string;
  token?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login';
  private userKey = 'user';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<User> {
    return this.http.post<User>(this.apiUrl, { username, password });
  }

  setUser(user: User): void {
    user.role = user.role.toLowerCase();
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  getUser(): User | null {
    const data = localStorage.getItem(this.userKey);
    return data ? (JSON.parse(data) as User) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getUser();
  }

  logout(): void {
    localStorage.removeItem(this.userKey);
  }
}
