import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountTC } from 'app/shared/model/account-tc.model';

type EntityResponseType = HttpResponse<IAccountTC>;
type EntityArrayResponseType = HttpResponse<IAccountTC[]>;

@Injectable({ providedIn: 'root' })
export class AccountTCService {
  public resourceUrl = SERVER_API_URL + 'api/account-tcs';

  constructor(protected http: HttpClient) {}

  create(accountTC: IAccountTC): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountTC);
    return this.http
      .post<IAccountTC>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountTC: IAccountTC): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountTC);
    return this.http
      .put<IAccountTC>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IAccountTC>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountTC[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountTC: IAccountTC): IAccountTC {
    const copy: IAccountTC = Object.assign({}, accountTC, {
      createdAt: accountTC.createdAt != null && accountTC.createdAt.isValid() ? accountTC.createdAt.toJSON() : null,
      updatedAt: accountTC.updatedAt != null && accountTC.updatedAt.isValid() ? accountTC.updatedAt.toJSON() : null,
      deletedAt: accountTC.deletedAt != null && accountTC.deletedAt.isValid() ? accountTC.deletedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
      res.body.deletedAt = res.body.deletedAt != null ? moment(res.body.deletedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountTC: IAccountTC) => {
        accountTC.createdAt = accountTC.createdAt != null ? moment(accountTC.createdAt) : null;
        accountTC.updatedAt = accountTC.updatedAt != null ? moment(accountTC.updatedAt) : null;
        accountTC.deletedAt = accountTC.deletedAt != null ? moment(accountTC.deletedAt) : null;
      });
    }
    return res;
  }
}
