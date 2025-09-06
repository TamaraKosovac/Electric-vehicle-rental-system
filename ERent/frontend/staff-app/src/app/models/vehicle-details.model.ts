import { Manufacturer } from './manufacturer.model';
import { Malfunction } from './malfunction.model';
import { VehicleState } from './enums/vehicle-state.enum';

export interface VehicleDetails {
  id: number;
  uniqueId: string;
  manufacturer: Manufacturer;
  model: string;
  purchasePrice: number;
  imagePath: string;
  state: VehicleState;
  currentLatitude: number;
  currentLongitude: number;
  malfunctions: Malfunction[];
}
