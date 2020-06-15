import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IUserInfo, UserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from './user-info.service';

@Component({
  selector: 'jhi-user-info-update',
  templateUrl: './user-info-update.component.html'
})
export class UserInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    dob: [null, [Validators.required]],
    gender: [],
    age: [],
    introduce: [],
    phone: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^0[0-9]{8}$')]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected userInfoService: UserInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userInfo }) => {
      this.updateForm(userInfo);
    });
  }

  updateForm(userInfo: IUserInfo) {
    this.editForm.patchValue({
      id: userInfo.id,
      dob: userInfo.dob != null ? userInfo.dob.format(DATE_TIME_FORMAT) : null,
      gender: userInfo.gender,
      age: userInfo.age,
      introduce: userInfo.introduce,
      phone: userInfo.phone
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
    const userInfo = this.createFromForm();
    if (userInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.userInfoService.update(userInfo));
    } else {
      this.subscribeToSaveResponse(this.userInfoService.create(userInfo));
    }
  }

  private createFromForm(): IUserInfo {
    return {
      ...new UserInfo(),
      id: this.editForm.get(['id']).value,
      dob: this.editForm.get(['dob']).value != null ? moment(this.editForm.get(['dob']).value, DATE_TIME_FORMAT) : undefined,
      gender: this.editForm.get(['gender']).value,
      age: this.editForm.get(['age']).value,
      introduce: this.editForm.get(['introduce']).value,
      phone: this.editForm.get(['phone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserInfo>>) {
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
}
