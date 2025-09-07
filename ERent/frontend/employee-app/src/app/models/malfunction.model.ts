export interface Malfunction {
  id: number;
  description: string;
  dateTime: string;
  vehicleId: number;
  manufacturerName?: string;
  vehicleModel?: string;

  date?: Date;
  time?: string;
}
