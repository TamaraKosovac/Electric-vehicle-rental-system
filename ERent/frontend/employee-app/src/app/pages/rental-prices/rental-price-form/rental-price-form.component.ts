import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { RentalPrice } from '../../../models/rental-price.model';

@Component({
  selector: 'app-rental-price-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './rental-price-form.component.html',
  styleUrls: ['./rental-price-form.component.css']
})
export class RentalPriceFormComponent {
  rentalPrice: RentalPrice;

  constructor(
    public dialogRef: MatDialogRef<RentalPriceFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { rentalPrice: RentalPrice }
  ) {
    this.rentalPrice = { ...data.rentalPrice };
  }

  save() {
    this.dialogRef.close(this.rentalPrice);
  }

  cancel() {
    this.dialogRef.close();
  }
}
