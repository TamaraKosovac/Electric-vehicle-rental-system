import { VehicleDetails } from './vehicle-details.model';

export interface BikeDetails extends VehicleDetails {
  autonomy: number;
}