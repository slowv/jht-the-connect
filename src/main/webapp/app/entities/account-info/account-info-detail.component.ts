import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAccountInfo } from 'app/shared/model/account-info.model';

@Component({
  selector: 'jhi-account-info-detail',
  templateUrl: './account-info-detail.component.html'
})
export class AccountInfoDetailComponent implements OnInit {
  accountInfo: IAccountInfo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountInfo }) => {
      this.accountInfo = accountInfo;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
