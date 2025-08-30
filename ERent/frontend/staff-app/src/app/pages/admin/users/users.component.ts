import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { UsersService } from '../../../services/users.service';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, MatTabsModule, MatTableModule, MatButtonModule, MatInputModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  clients: any[] = [];
  employees: any[] = [];
  displayedClientColumns: string[] = ['id', 'username', 'email', 'blocked', 'actions'];
  displayedEmployeeColumns: string[] = ['id', 'username', 'role', 'actions'];

  newEmployee: any = { username: '', password: '', firstName: '', lastName: '', role: '' };
  editingEmployee: any = null;

  constructor(private usersService: UsersService) {}

  ngOnInit() {
    this.loadClients();
    this.loadEmployees();
  }

  // --- Clients ---
  loadClients() {
    this.usersService.getClients().subscribe(data => this.clients = data);
  }

  blockClient(id: number) {
    this.usersService.blockClient(id).subscribe(() => this.loadClients());
  }

  unblockClient(id: number) {
    this.usersService.unblockClient(id).subscribe(() => this.loadClients());
  }

  // --- Employees ---
  loadEmployees() {
    this.usersService.getEmployees().subscribe(data => this.employees = data);
  }

  addEmployee() {
    this.usersService.createEmployee(this.newEmployee).subscribe(() => {
      this.newEmployee = { username: '', password: '', firstName: '', lastName: '', role: '' };
      this.loadEmployees();
    });
  }

  editEmployee(e: any) {
    this.editingEmployee = { ...e };
  }

  updateEmployee() {
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
}
