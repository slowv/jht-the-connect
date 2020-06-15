import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheconnectSharedModule } from 'app/shared/shared.module';
import { AccountTCComponent } from './account-tc.component';
import { AccountTCDetailComponent } from './account-tc-detail.component';
import { AccountTCUpdateComponent } from './account-tc-update.component';
import { AccountTCDeletePopupComponent, AccountTCDeleteDialogComponent } from './account-tc-delete-dialog.component';
import { accountTCRoute, accountTCPopupRoute } from './account-tc.route';

const ENTITY_STATES = [...accountTCRoute, ...accountTCPopupRoute];

@NgModule({
  imports: [TheconnectSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountTCComponent,
    AccountTCDetailComponent,
    AccountTCUpdateComponent,
    AccountTCDeleteDialogComponent,
    AccountTCDeletePopupComponent
  ],
  entryComponents: [AccountTCDeleteDialogComponent]
})
export class TheconnectAccountTCModule {}
