import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Manufacturer } from '../../../../models/manufacturer.model';

@Component({
  selector: 'app-manufacturer-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './manufacturer-form.component.html',
  styleUrls: ['./manufacturer-form.component.css']
})
export class ManufacturerFormComponent {
  manufacturer: Manufacturer = {
    id: 0,
    name: '',
    country: '',
    address: '',
    phone: '',
    fax: '',
    email: ''
  };

  constructor(
    public dialogRef: MatDialogRef<ManufacturerFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data?: { manufacturer?: Manufacturer }

  ) {
    if (data?.manufacturer) {
      this.manufacturer = { ...data.manufacturer };
    }
  }

  save() {
    this.dialogRef.close(this.manufacturer); 
  }

  cancel() {
    this.dialogRef.close();
  }
}
