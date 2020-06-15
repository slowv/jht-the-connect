export interface IAddress {
  id?: string;
  location?: string;
  districtId?: string;
  accountInfoId?: string;
}

export class Address implements IAddress {
  constructor(public id?: string, public location?: string, public districtId?: string, public accountInfoId?: string) {}
}
