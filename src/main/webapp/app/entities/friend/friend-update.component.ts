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
import { IFriend, Friend } from 'app/shared/model/friend.model';
import { FriendService } from './friend.service';
import { IAccountTC } from 'app/shared/model/account-tc.model';
import { AccountTCService } from 'app/entities/account-tc/account-tc.service';

@Component({
  selector: 'jhi-friend-update',
  templateUrl: './friend-update.component.html'
})
export class FriendUpdateComponent implements OnInit {
  isSaving: boolean;

  senders: IAccountTC[];

  receivers: IAccountTC[];

  editForm = this.fb.group({
    id: [],
    dateIsFriend: [],
    type: [null, [Validators.required]],
    senderId: [],
    receiverId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected friendService: FriendService,
    protected accountTCService: AccountTCService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ friend }) => {
      this.updateForm(friend);
    });
    this.accountTCService
      .query({ filter: 'friend-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountTC[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountTC[]>) => response.body)
      )
      .subscribe(
        (res: IAccountTC[]) => {
          if (!this.editForm.get('senderId').value) {
            this.senders = res;
          } else {
            this.accountTCService
              .find(this.editForm.get('senderId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAccountTC>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAccountTC>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAccountTC) => (this.senders = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.accountTCService
      .query({ filter: 'friend-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountTC[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountTC[]>) => response.body)
      )
      .subscribe(
        (res: IAccountTC[]) => {
          if (!this.editForm.get('receiverId').value) {
            this.receivers = res;
          } else {
            this.accountTCService
              .find(this.editForm.get('receiverId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAccountTC>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAccountTC>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAccountTC) => (this.receivers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(friend: IFriend) {
    this.editForm.patchValue({
      id: friend.id,
      dateIsFriend: friend.dateIsFriend != null ? friend.dateIsFriend.format(DATE_TIME_FORMAT) : null,
      type: friend.type,
      senderId: friend.senderId,
      receiverId: friend.receiverId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const friend = this.createFromForm();
    if (friend.id !== undefined) {
      this.subscribeToSaveResponse(this.friendService.update(friend));
    } else {
      this.subscribeToSaveResponse(this.friendService.create(friend));
    }
  }

  private createFromForm(): IFriend {
    return {
      ...new Friend(),
      id: this.editForm.get(['id']).value,
      dateIsFriend:
        this.editForm.get(['dateIsFriend']).value != null ? moment(this.editForm.get(['dateIsFriend']).value, DATE_TIME_FORMAT) : undefined,
      type: this.editForm.get(['type']).value,
      senderId: this.editForm.get(['senderId']).value,
      receiverId: this.editForm.get(['receiverId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFriend>>) {
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

  trackAccountTCById(index: number, item: IAccountTC) {
    return item.id;
  }
}
