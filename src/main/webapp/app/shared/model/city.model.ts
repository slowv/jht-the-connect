import { IDistrict } from 'app/shared/model/district.model';

export interface ICity {
  id?: string;
  name?: string;
  districts?: IDistrict[];
  countryId?: string;
}

export class City implements ICity {
  constructor(public id?: string, public name?: string, public districts?: IDistrict[], public countryId?: string) {}
}
