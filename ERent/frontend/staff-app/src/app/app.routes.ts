import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DashboardHomeComponent } from './pages/dashboard/dashboard-home.component';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { VehicleDetailsComponent } from './pages/vehicle-details/vehicle-details.component';
import { ManufacturersComponent } from './pages/manufacturers/manufacturers.component';
import { UsersComponent } from './pages/users/users.component';
import { AuthGuard } from './guards/auth.guard';
import { Role } from './models/enums/role.enum';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivateChild: [AuthGuard],
    children: [
      { path: '', component: DashboardHomeComponent },

      { 
        path: 'vehicles', 
        component: VehiclesComponent, 
        data: { title: 'Vehicles management', roles: [Role.ADMIN, Role.MANAGER] } 
      },
      { 
        path: 'vehicles/:type/:id', 
        component: VehicleDetailsComponent, 
        data: { title: 'Vehicle details', roles: [Role.ADMIN, Role.MANAGER] } 
      },
      { 
        path: 'manufacturers', 
        component: ManufacturersComponent, 
        data: { title: 'Manufacturers management', roles: [Role.ADMIN, Role.MANAGER] } 
      },
      { 
        path: 'users', 
        component: UsersComponent, 
        data: { title: 'Users management', roles: [Role.ADMIN, Role.MANAGER] } 
      },

      { 
        path: 'rentals', 
        loadComponent: () => import('./pages/rentals/rentals.component').then(m => m.RentalsComponent), 
        data: { title: 'Rentals management', roles: [Role.OPERATOR, Role.MANAGER] } 
      },
      { 
        path: 'rentals-map', 
        loadComponent: () => import('./pages/rentals-map/rentals-map.component').then(m => m.RentalsMapComponent), 
        data: { title: 'Rentals management', roles: [Role.OPERATOR, Role.MANAGER] } 
      },
      { 
        path: 'clients', 
        loadComponent: () => import('./pages/clients/clients.component').then(m => m.ClientsComponent), 
        data: { title: 'Clients management', roles: [Role.OPERATOR, Role.MANAGER] } 
      },
      { 
        path: 'malfunctions', 
        loadComponent: () => import('./pages/malfunctions/malfunctions.component').then(m => m.MalfunctionsComponent), 
        data: { title: 'Malfunctions management', roles: [Role.OPERATOR, Role.MANAGER] } 
      },

      { 
        path: 'statistics', 
        loadComponent: () => import('./pages/statistics/statistics.component').then(m => m.StatisticsComponent), 
        data: { title: 'Statistics management', roles: [Role.MANAGER] } 
      },
      { 
        path: 'rental-prices', 
        loadComponent: () => import('./pages/rental-prices/rental-prices.component').then(m => m.RentalPricesComponent), 
        data: { title: 'Rental prices management', roles: [Role.MANAGER] } 
      },
    ]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
