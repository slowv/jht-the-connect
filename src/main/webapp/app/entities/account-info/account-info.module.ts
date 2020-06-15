import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheconnectSharedModule } from 'app/shared/shared.module';
import { AccountInfoComponent } from './account-info.component';
import { AccountInfoDetailComponent } from './account-info-detail.component';
import { AccountInfoUpdateComponent } from './account-info-update.component';
import { AccountInfoDeletePopupComponent, AccountInfoDeleteDialogComponent } from './account-info-delete-dialog.component';
import { accountInfoRoute, accountInfoPopupRoute } from './account-info.route';

const ENTITY_STATES = [...accountInfoRoute, ...accountInfoPopupRoute];

@NgModule({
  imports: [TheconnectSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountInfoComponent,
    AccountInfoDetailComponent,
    AccountInfoUpdateComponent,
    AccountInfoDeleteDialogComponent,
    AccountInfoDeletePopupComponent
  ],
  entryComponents: [AccountInfoDeleteDialogComponent]
})
export class TheconnectAccountInfoModule {}
