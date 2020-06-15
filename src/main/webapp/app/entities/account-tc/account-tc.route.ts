import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountTC } from 'app/shared/model/account-tc.model';
import { AccountTCService } from './account-tc.service';
import { AccountTCComponent } from './account-tc.component';
import { AccountTCDetailComponent } from './account-tc-detail.component';
import { AccountTCUpdateComponent } from './account-tc-update.component';
import { AccountTCDeletePopupComponent } from './account-tc-delete-dialog.component';
import { IAccountTC } from 'app/shared/model/account-tc.model';

@Injectable({ providedIn: 'root' })
export class AccountTCResolve implements Resolve<IAccountTC> {
  constructor(private service: AccountTCService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountTC> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountTC>) => response.ok),
        map((accountTC: HttpResponse<AccountTC>) => accountTC.body)
      );
    }
    return of(new AccountTC());
  }
}

export const accountTCRoute: Routes = [
  {
    path: '',
    component: AccountTCComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'theconnectApp.accountTC.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountTCDetailComponent,
    resolve: {
      accountTC: AccountTCResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountTC.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountTCUpdateComponent,
    resolve: {
      accountTC: AccountTCResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountTC.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountTCUpdateComponent,
    resolve: {
      accountTC: AccountTCResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountTC.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountTCPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountTCDeletePopupComponent,
    resolve: {
      accountTC: AccountTCResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountTC.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
