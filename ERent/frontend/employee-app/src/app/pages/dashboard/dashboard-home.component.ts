import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard-home',
  template: '' 
})
export class DashboardHomeComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const user = this.authService.getUser();
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }

    const role = user.role.toLowerCase();

    if (role === 'admin' || role === 'manager') {
      this.router.navigate(['/dashboard/vehicles']);
    } else if (role === 'operator') {
      this.router.navigate(['/dashboard/rentals']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
