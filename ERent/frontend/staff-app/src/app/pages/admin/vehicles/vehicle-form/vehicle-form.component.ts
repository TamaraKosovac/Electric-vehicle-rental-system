import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { Manufacturer } from '../../../../models/manufacturer.model';
import { ManufacturersService } from '../../../../services/manufacturers.service';

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
    MatNativeDateModule,
    MatSelectModule
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './vehicle-form.component.html',
  styleUrls: ['./vehicle-form.component.css']
})
export class VehicleFormComponent implements OnInit {
  vehicle = {
    uniqueId: '',
    manufacturer: null as Manufacturer | null,
    model: '',
    purchasePrice: null,
    purchaseDate: null,
    description: '',
    autonomy: null,
    maxSpeed: null
  };

  selectedImage: File | null = null;
  manufacturers: Manufacturer[] = [];

  constructor(
    private manufacturersService: ManufacturersService,
    public dialogRef: MatDialogRef<VehicleFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { type: 'cars' | 'bikes' | 'scooters' }
  ) {}

  ngOnInit(): void {
    this.manufacturersService.getAll().subscribe((data: Manufacturer[]) => {
      this.manufacturers = data;
    });
  }

  save() {
    this.dialogRef.close({ vehicle: this.vehicle, image: this.selectedImage });
  }

  cancel() {
    this.dialogRef.close();
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedImage = file;
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
      this.selectedImage = file;
      console.log('Dropped image:', file);
    }
  }
}
