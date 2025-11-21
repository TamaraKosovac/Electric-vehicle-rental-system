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

import { Manufacturer } from '../../../models/manufacturer.model';
import { ManufacturerService } from '../../../services/manufacturer.service';
import { VehicleService } from '../../../services/vehicle.service';
import { Car } from '../../../models/car.model';
import { Bike } from '../../../models/bike.model';
import { Scooter } from '../../../models/scooter.model';
import { VehicleState } from '../../../models/enums/vehicle-state.enum';


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
  vehicle: Partial<Car & Bike & Scooter> = {
    uniqueId: '',
    manufacturer: undefined,
    model: '',
    purchasePrice: undefined,
    purchaseDate: undefined,
    description: '',
    autonomy: undefined,
    maxSpeed: undefined,
    state: VehicleState.AVAILABLE 
  };

  selectedImage: File | null = null;
  selectedImagePreview: string | null = null;
  manufacturers: Manufacturer[] = [];

  constructor(
    private manufacturerService: ManufacturerService,
    public vehicleService: VehicleService,
    public dialogRef: MatDialogRef<VehicleFormComponent>,
    @Inject(MAT_DIALOG_DATA) 
    public data: { 
      type: 'cars' | 'bikes' | 'scooters', 
      vehicle?: Car | Bike | Scooter 
    }
  ) {}

  ngOnInit(): void {
    this.manufacturerService.getAll().subscribe((data: Manufacturer[]) => {
      this.manufacturers = data;
    });

    if (this.data.vehicle) {
      this.vehicle = { ...this.data.vehicle };
    }
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

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedImagePreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.remove('dragover');
    const file = event.dataTransfer?.files[0];
    if (file) {
      this.selectedImage = file;

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedImagePreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.add('dragover');
  }

  onDragLeave(event: DragEvent) {
    (event.currentTarget as HTMLElement).classList.remove('dragover');
  }

  compareManufacturer(m1: Manufacturer, m2: Manufacturer): boolean {
    return m1 && m2 ? m1.id === m2.id : m1 === m2;
  }
}
