import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { IAccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html'
})
export class AddressUpdateComponent implements OnInit {
  isSaving: boolean;

  districts: IDistrict[];

  accountinfos: IAccountInfo[];

  editForm = this.fb.group({
    id: [],
    location: [null, [Validators.required]],
    districtId: [],
    accountInfoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected addressService: AddressService,
    protected districtService: DistrictService,
    protected accountInfoService: AccountInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);
    });
    this.districtService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDistrict[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDistrict[]>) => response.body)
      )
      .subscribe((res: IDistrict[]) => (this.districts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.accountInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAccountInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccountInfo[]>) => response.body)
      )
      .subscribe((res: IAccountInfo[]) => (this.accountinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(address: IAddress) {
    this.editForm.patchValue({
      id: address.id,
      location: address.location,
      districtId: address.districtId,
      accountInfoId: address.accountInfoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id']).value,
      location: this.editForm.get(['location']).value,
      districtId: this.editForm.get(['districtId']).value,
      accountInfoId: this.editForm.get(['accountInfoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>) {
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

  trackDistrictById(index: number, item: IDistrict) {
    return item.id;
  }

  trackAccountInfoById(index: number, item: IAccountInfo) {
    return item.id;
  }
}
