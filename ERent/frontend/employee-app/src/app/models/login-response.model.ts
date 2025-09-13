import { Role } from './enums/role.enum';

export interface LoginResponse {
  token: string;
  username: string;
  userId: number;
  role: Role;
}