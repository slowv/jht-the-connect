import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheconnectSharedModule } from 'app/shared/shared.module';
import { DistrictComponent } from './district.component';
import { DistrictDetailComponent } from './district-detail.component';
import { DistrictUpdateComponent } from './district-update.component';
import { DistrictDeletePopupComponent, DistrictDeleteDialogComponent } from './district-delete-dialog.component';
import { districtRoute, districtPopupRoute } from './district.route';

const ENTITY_STATES = [...districtRoute, ...districtPopupRoute];

@NgModule({
  imports: [TheconnectSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DistrictComponent,
    DistrictDetailComponent,
    DistrictUpdateComponent,
    DistrictDeleteDialogComponent,
    DistrictDeletePopupComponent
  ],
  entryComponents: [DistrictDeleteDialogComponent]
})
export class TheconnectDistrictModule {}
