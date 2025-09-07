import { VehicleType } from './enums/vehicle-type.enum';

export interface RentalPrice {
  id: number;
  vehicleType: VehicleType;
  pricePerHour: number;
}