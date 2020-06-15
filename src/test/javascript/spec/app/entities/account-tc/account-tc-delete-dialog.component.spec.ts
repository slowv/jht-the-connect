import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TheconnectTestModule } from '../../../test.module';
import { AccountTCDeleteDialogComponent } from 'app/entities/account-tc/account-tc-delete-dialog.component';
import { AccountTCService } from 'app/entities/account-tc/account-tc.service';

describe('Component Tests', () => {
  describe('AccountTC Management Delete Component', () => {
    let comp: AccountTCDeleteDialogComponent;
    let fixture: ComponentFixture<AccountTCDeleteDialogComponent>;
    let service: AccountTCService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountTCDeleteDialogComponent]
      })
        .overrideTemplate(AccountTCDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountTCDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountTCService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
