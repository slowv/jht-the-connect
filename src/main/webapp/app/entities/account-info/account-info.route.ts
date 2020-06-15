import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from './account-info.service';
import { AccountInfoComponent } from './account-info.component';
import { AccountInfoDetailComponent } from './account-info-detail.component';
import { AccountInfoUpdateComponent } from './account-info-update.component';
import { AccountInfoDeletePopupComponent } from './account-info-delete-dialog.component';
import { IAccountInfo } from 'app/shared/model/account-info.model';

@Injectable({ providedIn: 'root' })
export class AccountInfoResolve implements Resolve<IAccountInfo> {
  constructor(private service: AccountInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountInfo>) => response.ok),
        map((accountInfo: HttpResponse<AccountInfo>) => accountInfo.body)
      );
    }
    return of(new AccountInfo());
  }
}

export const accountInfoRoute: Routes = [
  {
    path: '',
    component: AccountInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'theconnectApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountInfoDetailComponent,
    resolve: {
      accountInfo: AccountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountInfoUpdateComponent,
    resolve: {
      accountInfo: AccountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountInfoUpdateComponent,
    resolve: {
      accountInfo: AccountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountInfoDeletePopupComponent,
    resolve: {
      accountInfo: AccountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
