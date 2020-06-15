import { ICity } from 'app/shared/model/city.model';

export interface ICountry {
  id?: string;
  name?: string;
  cities?: ICity[];
}

export class Country implements ICountry {
  constructor(public id?: string, public name?: string, public cities?: ICity[]) {}
}
