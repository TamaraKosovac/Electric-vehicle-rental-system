export interface Malfunction {
  id: number;
  description: string;
  dateTime: string;
  vehicleId: number;

  date?: Date;
  time?: string;
}
