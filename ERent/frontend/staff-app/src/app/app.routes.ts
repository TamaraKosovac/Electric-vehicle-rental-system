import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { OperatorDashboardComponent } from './pages/operator/operator-dashboard.component';
import { ManagerDashboardComponent } from './pages/manager/manager-dashboard.component';

import { VehiclesComponent } from './pages/admin/vehicles/vehicles.component';
import { VehicleDetailsComponent } from './pages/admin/vehicle-details/vehicle-details.component';
import { ManufacturersComponent } from './pages/admin/manufacturers/manufacturers.component';
import { UsersComponent } from './pages/admin/users/users.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'admin',
    component: AdminDashboardComponent,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'vehicles' },
      { path: 'vehicles', component: VehiclesComponent, data: { title: 'Vehicles management' } },
      { path: 'vehicles/:type/:id', component: VehicleDetailsComponent, data: { title: 'Vehicle details' } },
      { path: 'manufacturers', component: ManufacturersComponent, data: { title: 'Manufacturers management' } },
      { path: 'users', component: UsersComponent, data: { title: 'Users management' } }
    ]
  },

  { path: 'operator', component: OperatorDashboardComponent },
  { path: 'manager', component: ManagerDashboardComponent },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
