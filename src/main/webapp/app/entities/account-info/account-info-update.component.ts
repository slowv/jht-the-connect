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
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAccountInfo, AccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from './account-info.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';
import { IAccountTC } from 'app/shared/model/account-tc.model';
import { AccountTCService } from 'app/entities/account-tc/account-tc.service';

@Component({
  selector: 'jhi-account-info-update',
  templateUrl: './account-info-update.component.html'
})
export class AccountInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  images: IImage[];

  accounttcs: IAccountTC[];

  editForm = this.fb.group({
    id: [],
    dob: [null, [Validators.required]],
    gender: [],
    age: [],
    introduce: [],
    phone: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^0[0-9]{8}$')]],
    firstName: [],
    lastName: [],
    imageId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected accountInfoService: AccountInfoService,
    protected imageService: ImageService,
    protected accountTCService: AccountTCService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountInfo }) => {
      this.updateForm(accountInfo);
    });
    this.imageService
      .query({ filter: 'accountinfo-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IImage[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImage[]>) => response.body)
      )
      .subscribe(
        (res: IImage[]) => {
          if (!this.editForm.get('imageId').value) {
            this.images = res;
          } else {
            this.imageService
              .find(this.editForm.get('imageId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IImage>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IImage>) => subResponse.body)
              )
              .subscribe(
                (subRes: IImage) => (this.images = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.accountTCService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountTC[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountTC[]>) => response.body)
      )
      .subscribe((res: IAccountTC[]) => (this.accounttcs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(accountInfo: IAccountInfo) {
    this.editForm.patchValue({
      id: accountInfo.id,
      dob: accountInfo.dob != null ? accountInfo.dob.format(DATE_TIME_FORMAT) : null,
      gender: accountInfo.gender,
      age: accountInfo.age,
      introduce: accountInfo.introduce,
      phone: accountInfo.phone,
      firstName: accountInfo.firstName,
      lastName: accountInfo.lastName,
      imageId: accountInfo.imageId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountInfo = this.createFromForm();
    if (accountInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.accountInfoService.update(accountInfo));
    } else {
      this.subscribeToSaveResponse(this.accountInfoService.create(accountInfo));
    }
  }

  private createFromForm(): IAccountInfo {
    return {
      ...new AccountInfo(),
      id: this.editForm.get(['id']).value,
      dob: this.editForm.get(['dob']).value != null ? moment(this.editForm.get(['dob']).value, DATE_TIME_FORMAT) : undefined,
      gender: this.editForm.get(['gender']).value,
      age: this.editForm.get(['age']).value,
      introduce: this.editForm.get(['introduce']).value,
      phone: this.editForm.get(['phone']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      imageId: this.editForm.get(['imageId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountInfo>>) {
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

  trackImageById(index: number, item: IImage) {
    return item.id;
  }

  trackAccountTCById(index: number, item: IAccountTC) {
    return item.id;
  }
}
