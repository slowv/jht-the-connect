import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountTC } from 'app/shared/model/account-tc.model';
import { AccountTCService } from './account-tc.service';

@Component({
  selector: 'jhi-account-tc-delete-dialog',
  templateUrl: './account-tc-delete-dialog.component.html'
})
export class AccountTCDeleteDialogComponent {
  accountTC: IAccountTC;

  constructor(protected accountTCService: AccountTCService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.accountTCService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountTCListModification',
        content: 'Deleted an accountTC'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-tc-delete-popup',
  template: ''
})
export class AccountTCDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountTC }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountTCDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountTC = accountTC;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-tc', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-tc', { outlets: { popup: null } }]);
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
