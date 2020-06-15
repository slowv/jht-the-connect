import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IUserInfo {
  id?: string;
  dob?: Moment;
  gender?: Gender;
  age?: number;
  introduce?: any;
  phone?: string;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: string,
    public dob?: Moment,
    public gender?: Gender,
    public age?: number,
    public introduce?: any,
    public phone?: string
  ) {}
}
