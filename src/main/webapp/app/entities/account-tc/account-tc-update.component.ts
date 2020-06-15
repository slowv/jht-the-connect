import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountTC, AccountTC } from 'app/shared/model/account-tc.model';
import { AccountTCService } from './account-tc.service';
import { IAccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';

@Component({
  selector: 'jhi-account-tc-update',
  templateUrl: './account-tc-update.component.html'
})
export class AccountTCUpdateComponent implements OnInit {
  isSaving: boolean;

  accountinfos: IAccountInfo[];

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    password: [null, [Validators.required]],
    status: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    deletedAt: [null, [Validators.required]],
    accountInfoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected accountTCService: AccountTCService,
    protected accountInfoService: AccountInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountTC }) => {
      this.updateForm(accountTC);
    });
    this.accountInfoService
      .query({ filter: 'accounttc-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountInfo[]>) => response.body)
      )
      .subscribe(
        (res: IAccountInfo[]) => {
          if (!this.editForm.get('accountInfoId').value) {
            this.accountinfos = res;
          } else {
            this.accountInfoService
              .find(this.editForm.get('accountInfoId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAccountInfo>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAccountInfo>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAccountInfo) => (this.accountinfos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(accountTC: IAccountTC) {
    this.editForm.patchValue({
      id: accountTC.id,
      email: accountTC.email,
      password: accountTC.password,
      status: accountTC.status,
      createdAt: accountTC.createdAt != null ? accountTC.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: accountTC.updatedAt != null ? accountTC.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: accountTC.deletedAt != null ? accountTC.deletedAt.format(DATE_TIME_FORMAT) : null,
      accountInfoId: accountTC.accountInfoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountTC = this.createFromForm();
    if (accountTC.id !== undefined) {
      this.subscribeToSaveResponse(this.accountTCService.update(accountTC));
    } else {
      this.subscribeToSaveResponse(this.accountTCService.create(accountTC));
    }
  }

  private createFromForm(): IAccountTC {
    return {
      ...new AccountTC(),
      id: this.editForm.get(['id']).value,
      email: this.editForm.get(['email']).value,
      password: this.editForm.get(['password']).value,
      status: this.editForm.get(['status']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      accountInfoId: this.editForm.get(['accountInfoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountTC>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAccountInfoById(index: number, item: IAccountInfo) {
    return item.id;
  }
}
