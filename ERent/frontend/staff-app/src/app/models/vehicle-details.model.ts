import { Manufacturer } from './manufacturer.model';
import { Malfunction } from './malfunction.model';

export interface VehicleDetails {
  id: number;
  uniqueId: string;
  manufacturer: Manufacturer;
  model: string;
  purchasePrice: number;
  imagePath: string;
  rented: boolean;
  currentLatitude: number;
  currentLongitude: number;
  malfunctions: Malfunction[];
}
