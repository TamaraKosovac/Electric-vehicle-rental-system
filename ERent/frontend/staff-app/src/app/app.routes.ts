import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DashboardHomeComponent } from './pages/dashboard/dashboard-home.component';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { VehicleDetailsComponent } from './pages/vehicle-details/vehicle-details.component';
import { ManufacturersComponent } from './pages/manufacturers/manufacturers.component';
import { UsersComponent } from './pages/users/users.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivateChild: [AuthGuard],
    children: [
    { path: '', component: DashboardHomeComponent },

      // admin + manager
      { path: 'vehicles', component: VehiclesComponent, data: { title: 'Vehicles management', roles: ['admin','manager'] } },
      { path: 'vehicles/:type/:id', component: VehicleDetailsComponent, data: { title: 'Vehicle details', roles: ['admin','manager'] } },
      { path: 'manufacturers', component: ManufacturersComponent, data: { title: 'Manufacturers management', roles: ['admin','manager'] } },
      { path: 'users', component: UsersComponent, data: { title: 'Users management', roles: ['admin','manager'] } },

      // operator + manager
      { path: 'rentals', loadComponent: () => import('./pages/rentals/rentals.component').then(m => m.RentalsComponent), data: { title: 'Rentals management', roles: ['operator','manager'] } },
      { path: 'rentals-map', loadComponent: () => import('./pages/rentals-map/rentals-map.component').then(m => m.RentalsMapComponent), data: { title: 'Rentals management', roles: ['operator','manager'] } },
      { path: 'clients', loadComponent: () => import('./pages/clients/clients.component').then(m => m.ClientsComponent), data: { title: 'Clients management', roles: ['operator','manager'] } },
      { path: 'malfunctions', loadComponent: () => import('./pages/malfunctions/malfunctions.component').then(m => m.MalfunctionsComponent), data: { title: 'Malfunctions management', roles: ['operator','manager'] } },

      // samo manager
      { path: 'statistics', loadComponent: () => import('./pages/statistics/statistics.component').then(m => m.StatisticsComponent), data: { title: 'Statistics management', roles: ['manager'] } },
      { path: 'rental-prices', loadComponent: () => import('./pages/rental-prices/rental-prices.component').then(m => m.RentalPricesComponent), data: { title: 'Rental prices management', roles: ['manager'] } },
    ]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
