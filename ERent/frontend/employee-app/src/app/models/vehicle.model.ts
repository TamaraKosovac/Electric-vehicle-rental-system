import { VehicleState } from './enums/vehicle-state.enum';

export interface Vehicle {
  id: number;
  uniqueId: string;
  manufacturer: string;       
  model: string;
  purchasePrice: number;
  imagePath: string;
  state: VehicleState;  
  currentLatitude: number;
  currentLongitude: number;
}
