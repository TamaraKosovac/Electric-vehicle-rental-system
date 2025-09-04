import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

import { Employee } from '../../../models/employee.model';
import { EmployeeRole } from '../../../models/enums/employee-role.enum';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule
  ],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {
  employee: Employee = {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    role: null as any
  };

  roles = Object.values(EmployeeRole);

  constructor(
    public dialogRef: MatDialogRef<UserFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data?: { employee?: Employee }
  ) {
    if (data?.employee) {
      this.employee = { ...data.employee }; 
    }
  }

  save() {
    this.dialogRef.close(this.employee);
  }

  cancel() {
    this.dialogRef.close();
  }
}
