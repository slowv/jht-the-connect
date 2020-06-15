import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.TheconnectCountryModule)
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.TheconnectCityModule)
      },
      {
        path: 'district',
        loadChildren: () => import('./district/district.module').then(m => m.TheconnectDistrictModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.TheconnectAddressModule)
      },
      {
        path: 'user-info',
        loadChildren: () => import('./user-info/user-info.module').then(m => m.TheconnectUserInfoModule)
      },
      {
        path: 'account-info',
        loadChildren: () => import('./account-info/account-info.module').then(m => m.TheconnectAccountInfoModule)
      },
      {
        path: 'account-tc',
        loadChildren: () => import('./account-tc/account-tc.module').then(m => m.TheconnectAccountTCModule)
      },
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.TheconnectImageModule)
      },
      {
        path: 'friend',
        loadChildren: () => import('./friend/friend.module').then(m => m.TheconnectFriendModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TheconnectEntityModule {}
