import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheconnectSharedModule } from 'app/shared/shared.module';
import { FriendComponent } from './friend.component';
import { FriendDetailComponent } from './friend-detail.component';
import { FriendUpdateComponent } from './friend-update.component';
import { FriendDeletePopupComponent, FriendDeleteDialogComponent } from './friend-delete-dialog.component';
import { friendRoute, friendPopupRoute } from './friend.route';

const ENTITY_STATES = [...friendRoute, ...friendPopupRoute];

@NgModule({
  imports: [TheconnectSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FriendComponent, FriendDetailComponent, FriendUpdateComponent, FriendDeleteDialogComponent, FriendDeletePopupComponent],
  entryComponents: [FriendDeleteDialogComponent]
})
export class TheconnectFriendModule {}
