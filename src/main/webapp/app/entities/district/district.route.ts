import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { District } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';
import { DistrictComponent } from './district.component';
import { DistrictDetailComponent } from './district-detail.component';
import { DistrictUpdateComponent } from './district-update.component';
import { DistrictDeletePopupComponent } from './district-delete-dialog.component';
import { IDistrict } from 'app/shared/model/district.model';

@Injectable({ providedIn: 'root' })
export class DistrictResolve implements Resolve<IDistrict> {
  constructor(private service: DistrictService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDistrict> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<District>) => response.ok),
        map((district: HttpResponse<District>) => district.body)
      );
    }
    return of(new District());
  }
}

export const districtRoute: Routes = [
  {
    path: '',
    component: DistrictComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'theconnectApp.district.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DistrictDetailComponent,
    resolve: {
      district: DistrictResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.district.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DistrictUpdateComponent,
    resolve: {
      district: DistrictResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.district.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DistrictUpdateComponent,
    resolve: {
      district: DistrictResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.district.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const districtPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DistrictDeletePopupComponent,
    resolve: {
      district: DistrictResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'theconnectApp.district.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
