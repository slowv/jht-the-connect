import { Moment } from 'moment';

export interface IImage {
  id?: string;
  url?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  status?: number;
  accountInfoId?: string;
}

export class Image implements IImage {
  constructor(
    public id?: string,
    public url?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public status?: number,
    public accountInfoId?: string
  ) {}
}
