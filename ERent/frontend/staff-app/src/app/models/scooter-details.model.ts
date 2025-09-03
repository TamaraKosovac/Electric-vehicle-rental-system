import { VehicleDetails } from './vehicle-details.model';

export interface ScooterDetails extends VehicleDetails {
  maxSpeed: number;
}