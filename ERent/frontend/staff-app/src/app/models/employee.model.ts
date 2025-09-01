import { User } from './user.model';
import { EmployeeRole } from './enums/employee-role.enum';

export interface Employee extends User {
  role: EmployeeRole;
}
