import { Moment } from 'moment';

export interface IAccountTC {
  id?: string;
  email?: string;
  password?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  accountInfoId?: string;
}

export class AccountTC implements IAccountTC {
  constructor(
    public id?: string,
    public email?: string,
    public password?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public accountInfoId?: string
  ) {}
}
