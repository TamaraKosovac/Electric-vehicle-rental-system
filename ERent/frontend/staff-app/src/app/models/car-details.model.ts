import { VehicleDetails } from './vehicle-details.model';

export interface CarDetails extends VehicleDetails {
  purchaseDate: string; 
  description: string;
}
