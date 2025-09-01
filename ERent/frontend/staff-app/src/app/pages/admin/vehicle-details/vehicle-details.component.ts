import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { VehiclesService } from '../../../services/vehicles.service';
import { Car } from '../../../models/car.model';
import { Bike } from '../../../models/bike.model';
import { Scooter } from '../../../models/scooter.model';
import { Malfunction } from '../../../models/malfunction.model';

@Component({
  selector: 'app-vehicle-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {
  vehicleType!: 'cars' | 'bikes' | 'scooters';
  vehicleId!: number;

  vehicle!: Car | Bike | Scooter;

  malfunctions: Malfunction[] = [];
  rentals: any[] = []; 

  constructor(
    private route: ActivatedRoute,
    private vehiclesService: VehiclesService
  ) {}

  ngOnInit(): void {
    this.vehicleType = this.route.snapshot.paramMap.get('type') as 'cars' | 'bikes' | 'scooters';
    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));

    this.vehiclesService.getVehicleById(this.vehicleType, this.vehicleId)
      .subscribe(v => this.vehicle = v);

    this.vehiclesService.getMalfunctions(this.vehicleType, this.vehicleId)
      .subscribe(m => this.malfunctions = m);

    this.vehiclesService.getRentals(this.vehicleType, this.vehicleId)
      .subscribe(r => this.rentals = r);
  }

  addMalfunction(desc: string) {
    this.vehiclesService.addMalfunction(this.vehicleType, this.vehicleId, { description: desc } as Malfunction)
      .subscribe(newM => this.malfunctions.push(newM));
  }

  deleteMalfunction(id: number) {
    this.vehiclesService.deleteMalfunction(this.vehicleType, id).subscribe(() => {
      this.malfunctions = this.malfunctions.filter(m => m.id !== id);
    });
  }
}
