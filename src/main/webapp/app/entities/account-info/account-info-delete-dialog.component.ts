import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountInfo } from 'app/shared/model/account-info.model';
import { AccountInfoService } from './account-info.service';

@Component({
  selector: 'jhi-account-info-delete-dialog',
  templateUrl: './account-info-delete-dialog.component.html'
})
export class AccountInfoDeleteDialogComponent {
  accountInfo: IAccountInfo;

  constructor(
    protected accountInfoService: AccountInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.accountInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountInfoListModification',
        content: 'Deleted an accountInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-info-delete-popup',
  template: ''
})
export class AccountInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountInfo = accountInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
