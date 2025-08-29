import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AdminComponent } from './pages/admin/admin.component';
import { OperatorComponent } from './pages/operator/operator.component';
import { ManagerComponent } from './pages/manager/manager.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'operator', component: OperatorComponent },
  { path: 'manager', component: ManagerComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
