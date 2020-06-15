import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheconnectSharedModule } from 'app/shared/shared.module';
import { UserInfoComponent } from './user-info.component';
import { UserInfoDetailComponent } from './user-info-detail.component';
import { UserInfoUpdateComponent } from './user-info-update.component';
import { UserInfoDeletePopupComponent, UserInfoDeleteDialogComponent } from './user-info-delete-dialog.component';
import { userInfoRoute, userInfoPopupRoute } from './user-info.route';

const ENTITY_STATES = [...userInfoRoute, ...userInfoPopupRoute];

@NgModule({
  imports: [TheconnectSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserInfoComponent,
    UserInfoDetailComponent,
    UserInfoUpdateComponent,
    UserInfoDeleteDialogComponent,
    UserInfoDeletePopupComponent
  ],
  entryComponents: [UserInfoDeleteDialogComponent]
})
export class TheconnectUserInfoModule {}
