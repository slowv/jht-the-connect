import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountTC } from 'app/shared/model/account-tc.model';

@Component({
  selector: 'jhi-account-tc-detail',
  templateUrl: './account-tc-detail.component.html'
})
export class AccountTCDetailComponent implements OnInit {
  accountTC: IAccountTC;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountTC }) => {
      this.accountTC = accountTC;
    });
  }

  previousState() {
    window.history.back();
  }
}
