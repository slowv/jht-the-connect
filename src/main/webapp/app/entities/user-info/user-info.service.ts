import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserInfo } from 'app/shared/model/user-info.model';

type EntityResponseType = HttpResponse<IUserInfo>;
type EntityArrayResponseType = HttpResponse<IUserInfo[]>;

@Injectable({ providedIn: 'root' })
export class UserInfoService {
  public resourceUrl = SERVER_API_URL + 'api/user-infos';

  constructor(protected http: HttpClient) {}

  create(userInfo: IUserInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInfo);
    return this.http
      .post<IUserInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userInfo: IUserInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInfo);
    return this.http
      .put<IUserInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IUserInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userInfo: IUserInfo): IUserInfo {
    const copy: IUserInfo = Object.assign({}, userInfo, {
      dob: userInfo.dob != null && userInfo.dob.isValid() ? userInfo.dob.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob != null ? moment(res.body.dob) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userInfo: IUserInfo) => {
        userInfo.dob = userInfo.dob != null ? moment(userInfo.dob) : null;
      });
    }
    return res;
  }
}
