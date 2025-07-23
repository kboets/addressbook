import { Country } from './country';

export interface Address {
  id?: number;
  street: string;
  houseNumber: string;
  box?: string;
  zipCode: string;
  city: string;
  countryDto: Country;
}
