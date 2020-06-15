import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountInfo } from 'app/shared/model/account-info.model';

type EntityResponseType = HttpResponse<IAccountInfo>;
type EntityArrayResponseType = HttpResponse<IAccountInfo[]>;

@Injectable({ providedIn: 'root' })
export class AccountInfoService {
  public resourceUrl = SERVER_API_URL + 'api/account-infos';

  constructor(protected http: HttpClient) {}

  create(accountInfo: IAccountInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountInfo);
    return this.http
      .post<IAccountInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountInfo: IAccountInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountInfo);
    return this.http
      .put<IAccountInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IAccountInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountInfo: IAccountInfo): IAccountInfo {
    const copy: IAccountInfo = Object.assign({}, accountInfo, {
      dob: accountInfo.dob != null && accountInfo.dob.isValid() ? accountInfo.dob.toJSON() : null
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
      res.body.forEach((accountInfo: IAccountInfo) => {
        accountInfo.dob = accountInfo.dob != null ? moment(accountInfo.dob) : null;
      });
    }
    return res;
  }
}
