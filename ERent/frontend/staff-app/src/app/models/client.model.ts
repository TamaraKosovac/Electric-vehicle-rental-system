import { User } from './user.model';
import { DocumentType } from './enums/document-type.enum';

export interface Client extends User {
  documentType: DocumentType;
  documentNumber: string;
  drivingLicense: string;
  email: string;
  phone: string;
  avatarPath: string;
  blocked: boolean;
}
