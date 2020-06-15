import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFriend } from 'app/shared/model/friend.model';

type EntityResponseType = HttpResponse<IFriend>;
type EntityArrayResponseType = HttpResponse<IFriend[]>;

@Injectable({ providedIn: 'root' })
export class FriendService {
  public resourceUrl = SERVER_API_URL + 'api/friends';

  constructor(protected http: HttpClient) {}

  create(friend: IFriend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(friend);
    return this.http
      .post<IFriend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(friend: IFriend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(friend);
    return this.http
      .put<IFriend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IFriend>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFriend[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(friend: IFriend): IFriend {
    const copy: IFriend = Object.assign({}, friend, {
      dateIsFriend: friend.dateIsFriend != null && friend.dateIsFriend.isValid() ? friend.dateIsFriend.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateIsFriend = res.body.dateIsFriend != null ? moment(res.body.dateIsFriend) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((friend: IFriend) => {
        friend.dateIsFriend = friend.dateIsFriend != null ? moment(friend.dateIsFriend) : null;
      });
    }
    return res;
  }
}
