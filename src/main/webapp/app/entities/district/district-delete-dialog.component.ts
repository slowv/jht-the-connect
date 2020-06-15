import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';

@Component({
  selector: 'jhi-district-delete-dialog',
  templateUrl: './district-delete-dialog.component.html'
})
export class DistrictDeleteDialogComponent {
  district: IDistrict;

  constructor(protected districtService: DistrictService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.districtService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'districtListModification',
        content: 'Deleted an district'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-district-delete-popup',
  template: ''
})
export class DistrictDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ district }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DistrictDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.district = district;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/district', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/district', { outlets: { popup: null } }]);
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
