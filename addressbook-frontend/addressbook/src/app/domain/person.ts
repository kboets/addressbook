import { Address } from './address';

export interface Person {
  id?: number;
  name: string;
  firstName: string;
  birthDate?: Date;
  phoneNumber?: string;
  mobilePhone: string;
  email: string;
  mainAddress?: Address;
}
