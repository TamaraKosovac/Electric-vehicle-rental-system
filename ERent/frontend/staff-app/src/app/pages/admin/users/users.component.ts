import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';

import { UsersService } from '../../../services/users.service';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { MatIconModule } from '@angular/material/icon';

import { Client } from '../../../models/client.model';
import { Employee } from '../../../models/employee.model';
import { EmployeeRole } from '../../../models/enums/employee-role.enum';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    DataTableComponent,
    MatIconModule
  ],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  // 👇 koristi modele umesto any
  clients: Client[] = [];
  employees: Employee[] = [];

  currentClientPage = 1;
  clientTotalPages = 1;

  currentEmployeePage = 1;
  employeeTotalPages = 1;

  @ViewChild('paginatorClients') paginatorClients!: MatPaginator;
  @ViewChild('paginatorEmployees') paginatorEmployees!: MatPaginator;

  newEmployee: Employee = {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    role: EmployeeRole.OPERATOR
  };

  editingEmployee: Employee | null = null;

  constructor(private usersService: UsersService) {}

  activeTab: 'clients' | 'employees' = 'clients';
  activeTabIndex = 0;

  onTabChange(event: any) {
    if (event.index === 0) this.activeTab = 'clients';
    if (event.index === 1) this.activeTab = 'employees';
  }

  ngOnInit() {
    this.loadClients();
    this.loadEmployees();
  }

  loadClients() {
    this.usersService.getClients().subscribe(data => {
      this.clients = data.map(c => ({
        ...c,
        fullName: `${c.firstName} ${c.lastName}`
      }));
      this.clientTotalPages = 1;
    });
  }

  blockUnblockClient(client: Client) {
    if (client.blocked) {
      this.usersService.unblockClient(client.id).subscribe(() => this.loadClients());
    } else {
      this.usersService.blockClient(client.id).subscribe(() => this.loadClients());
    }
  }

  loadEmployees() {
    this.usersService.getEmployees().subscribe(data => {
      this.employees = data.map(e => ({
        ...e,
        fullName: `${e.firstName} ${e.lastName}`
      }));
      this.employeeTotalPages = 1;
    });
  }

  addEmployee() {
    this.usersService.createEmployee(this.newEmployee).subscribe(() => {
      this.newEmployee = {
        id: 0,
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        role: EmployeeRole.OPERATOR
      };
      this.loadEmployees();
    });
  }

  editEmployee(e: Employee) {
    this.editingEmployee = { ...e };
  }

  updateEmployee() {
    if (!this.editingEmployee) return;
    this.usersService.updateEmployee(this.editingEmployee.id, this.editingEmployee).subscribe(() => {
      this.editingEmployee = null;
      this.loadEmployees();
    });
  }

  deleteEmployee(id: number) {
    this.usersService.deleteEmployee(id).subscribe(() => {
      this.employees = this.employees.filter(e => e.id !== id);
    });
  }

  applyFilter(event: Event, type: 'clients' | 'employees') {
    const value = (event.target as HTMLInputElement).value.trim().toLowerCase();
    if (type === 'clients') {
      this.clients = this.clients.filter(c =>
        c.username.toLowerCase().includes(value) || c.email.toLowerCase().includes(value)
      );
    }
    if (type === 'employees') {
      this.employees = this.employees.filter(e =>
        e.username.toLowerCase().includes(value) || e.role.toLowerCase().includes(value)
      );
    }
  }
}
