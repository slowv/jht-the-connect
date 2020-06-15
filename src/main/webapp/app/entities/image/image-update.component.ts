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
import { IImage, Image } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { IAccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';

@Component({
  selector: 'jhi-image-update',
  templateUrl: './image-update.component.html'
})
export class ImageUpdateComponent implements OnInit {
  isSaving: boolean;

  accountinfos: IAccountInfo[];

  editForm = this.fb.group({
    id: [],
    url: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    deletedAt: [],
    status: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected imageService: ImageService,
    protected accountInfoService: AccountInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ image }) => {
      this.updateForm(image);
    });
    this.accountInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountInfo[]>) => response.body)
      )
      .subscribe((res: IAccountInfo[]) => (this.accountinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(image: IImage) {
    this.editForm.patchValue({
      id: image.id,
      url: image.url,
      createdAt: image.createdAt != null ? image.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: image.updatedAt != null ? image.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: image.deletedAt != null ? image.deletedAt.format(DATE_TIME_FORMAT) : null,
      status: image.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const image = this.createFromForm();
    if (image.id !== undefined) {
      this.subscribeToSaveResponse(this.imageService.update(image));
    } else {
      this.subscribeToSaveResponse(this.imageService.create(image));
    }
  }

  private createFromForm(): IImage {
    return {
      ...new Image(),
      id: this.editForm.get(['id']).value,
      url: this.editForm.get(['url']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>) {
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
