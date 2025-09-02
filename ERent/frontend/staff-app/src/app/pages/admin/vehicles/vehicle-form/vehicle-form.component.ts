import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-vehicle-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [provideNativeDateAdapter()],   
  templateUrl: './vehicle-form.component.html',
  styleUrls: ['./vehicle-form.component.css']
})
export class VehicleFormComponent {
  vehicle: any = {
    uniqueId: '',
    manufacturer: '',
    model: '',
    purchasePrice: 0,
    imagePath: '',
    rented: false,
    hasMalfunctions: false,
    purchaseDate: null
  };

  constructor(
    public dialogRef: MatDialogRef<VehicleFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { type: 'cars' | 'bikes' | 'scooters' }
  ) {}

  save() {
    console.log('Saving', this.data.type, this.vehicle);
    this.dialogRef.close(this.vehicle);
  }

  cancel() {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
      const file = event.target.files[0];
      if (file) {
        this.vehicle.imagePath = file.name;
        console.log('Selected image:', file);
      }
    }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.add('dragover');
  }

  onDragLeave(event: DragEvent) {
    (event.currentTarget as HTMLElement).classList.remove('dragover');
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.remove('dragover');
    const file = event.dataTransfer?.files[0];
    if (file) {
      this.vehicle.imagePath = file.name;
      console.log('Dropped image:', file);
    }
  }
}
