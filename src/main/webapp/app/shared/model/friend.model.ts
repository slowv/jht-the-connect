import { Moment } from 'moment';
import { FriendType } from 'app/shared/model/enumerations/friend-type.model';

export interface IFriend {
  id?: string;
  dateIsFriend?: Moment;
  type?: FriendType;
  senderId?: string;
  receiverId?: string;
}

export class Friend implements IFriend {
  constructor(
    public id?: string,
    public dateIsFriend?: Moment,
    public type?: FriendType,
    public senderId?: string,
    public receiverId?: string
  ) {}
}
