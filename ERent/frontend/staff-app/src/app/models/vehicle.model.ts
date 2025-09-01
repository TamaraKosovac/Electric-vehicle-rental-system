import { Malfunction } from './malfunction.model';

export interface Vehicle {
  id: number;
  uniqueId: string;
  manufacturer: string;       
  model: string;
  purchasePrice: number;
  imagePath: string;
  rented: boolean;
  hasMalfunctions: boolean;  
}
