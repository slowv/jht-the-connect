import { IAddress } from 'app/shared/model/address.model';

export interface IDistrict {
  id?: string;
  name?: string;
  addresses?: IAddress[];
  cityId?: string;
}

export class District implements IDistrict {
  constructor(public id?: string, public name?: string, public addresses?: IAddress[], public cityId?: string) {}
}
