import { User } from './user.model';
import { Role } from './enums/role.enum';

export interface Employee extends User {
  role: Role;
}
