import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

import { UsersService } from '../../../services/users.service';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { MatIconModule } from '@angular/material/icon';
import { MinimalPaginatorComponent } from '../../../shared/minimal-paginator/minimal-paginator.component';

import { Client } from '../../../models/client.model';
import { Employee } from '../../../models/employee.model';
import { EmployeeRole } from '../../../models/enums/employee-role.enum';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UserFormComponent } from './user-form/user-form.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';


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
    MatIconModule,
    MinimalPaginatorComponent,
    MatSnackBarModule
  ],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  allClients: Client[] = [];
  allEmployees: Employee[] = [];

  clients: Client[] = [];
  employees: Employee[] = [];

  currentClientPage = 1;
  clientTotalPages = 1;

  currentEmployeePage = 1;
  employeeTotalPages = 1;

  pageSize = 10; 

  newEmployee: Employee = {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    role: EmployeeRole.OPERATOR
  };

  editingEmployee: Employee | null = null;

  constructor(private usersService: UsersService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

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
      this.allClients = data.map(c => ({
        ...c,
        fullName: `${c.firstName} ${c.lastName}`
      }));
      this.setClientPage(1);
    });
  }

  setClientPage(page: number) {
    this.currentClientPage = page;
    const start = (page - 1) * this.pageSize;
    this.clients = this.allClients.slice(start, start + this.pageSize);
    this.clientTotalPages = Math.ceil(this.allClients.length / this.pageSize);
  }

  previousClientPage() {
    if (this.currentClientPage > 1) {
      this.setClientPage(this.currentClientPage - 1);
    }
  }

  nextClientPage() {
    if (this.currentClientPage < this.clientTotalPages) {
      this.setClientPage(this.currentClientPage + 1);
    }
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
      this.allEmployees = data.map(e => ({
        ...e,
        fullName: `${e.firstName} ${e.lastName}`
      }));
      this.setEmployeePage(1);
    });
  }

  setEmployeePage(page: number) {
    this.currentEmployeePage = page;
    const start = (page - 1) * this.pageSize;
    this.employees = this.allEmployees.slice(start, start + this.pageSize);
    this.employeeTotalPages = Math.ceil(this.allEmployees.length / this.pageSize);
  }

  previousEmployeePage() {
    if (this.currentEmployeePage > 1) {
      this.setEmployeePage(this.currentEmployeePage - 1);
    }
  }

  nextEmployeePage() {
    if (this.currentEmployeePage < this.employeeTotalPages) {
      this.setEmployeePage(this.currentEmployeePage + 1);
    }
  }

  addEmployee() {
    const dialogRef = this.dialog.open(UserFormComponent, { width: '600px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (!result.id || result.id === 0) {
          delete result.id;
        }

        this.usersService.createEmployee(result).subscribe({
          next: () => {
            this.loadEmployees();
            this.snackBar.open('Employee created successfully!', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Failed to create employee.', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-error']
            });
          }
        });
      }
    });
  }

  editEmployee(e: Employee) {
    const dialogRef = this.dialog.open(UserFormComponent, {
      width: '600px',
      data: { employee: e }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.usersService.updateEmployee(e.id, result).subscribe({
          next: () => {
            this.loadEmployees();
            this.snackBar.open('Employee updated successfully!', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Failed to update employee.', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-error']
            });
          }
        });
      }
    });
  }

  deleteEmployee(id: number) {
    this.usersService.deleteEmployee(id).subscribe({
      next: () => {
        this.allEmployees = this.allEmployees.filter(e => e.id !== id);
        this.setEmployeePage(this.currentEmployeePage);
        this.snackBar.open('Employee deleted successfully!', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-success']
        });
      },
      error: () => {
        this.snackBar.open('Failed to delete employee.', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  applyFilter(event: Event, type: 'clients' | 'employees') {
    const value = (event.target as HTMLInputElement).value.trim().toLowerCase();

    if (type === 'clients') {
      const filtered = value
        ? this.allClients.filter(c =>
            c.username.toLowerCase().includes(value) ||
            c.email.toLowerCase().includes(value) ||
            `${c.firstName} ${c.lastName}`.toLowerCase().includes(value)
          )
        : [...this.allClients];
      this.allClients = filtered;
      this.setClientPage(1);
    }

    if (type === 'employees') {
      const filtered = value
        ? this.allEmployees.filter(e =>
            e.username.toLowerCase().includes(value) ||
            e.role.toLowerCase().includes(value) ||
            `${e.firstName} ${e.lastName}`.toLowerCase().includes(value)
          )
        : [...this.allEmployees];
      this.allEmployees = filtered;
      this.setEmployeePage(1);
    }
  }
}
