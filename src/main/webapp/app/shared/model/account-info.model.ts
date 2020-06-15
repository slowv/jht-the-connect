import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IAccountInfo {
  id?: string;
  dob?: Moment;
  gender?: Gender;
  age?: number;
  introduce?: any;
  phone?: string;
  firstName?: string;
  lastName?: string;
  imageId?: string;
  addresses?: IAddress[];
  accountTCId?: string;
}

export class AccountInfo implements IAccountInfo {
  constructor(
    public id?: string,
    public dob?: Moment,
    public gender?: Gender,
    public age?: number,
    public introduce?: any,
    public phone?: string,
    public firstName?: string,
    public lastName?: string,
    public imageId?: string,
    public addresses?: IAddress[],
    public accountTCId?: string
  ) {}
}
