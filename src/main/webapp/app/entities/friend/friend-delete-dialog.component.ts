import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFriend } from 'app/shared/model/friend.model';
import { FriendService } from './friend.service';

@Component({
  selector: 'jhi-friend-delete-dialog',
  templateUrl: './friend-delete-dialog.component.html'
})
export class FriendDeleteDialogComponent {
  friend: IFriend;

  constructor(protected friendService: FriendService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.friendService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'friendListModification',
        content: 'Deleted an friend'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-friend-delete-popup',
  template: ''
})
export class FriendDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ friend }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FriendDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.friend = friend;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/friend', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/friend', { outlets: { popup: null } }]);
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
