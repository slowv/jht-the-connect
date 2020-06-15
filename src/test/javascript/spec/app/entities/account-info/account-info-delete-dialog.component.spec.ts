import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TheconnectTestModule } from '../../../test.module';
import { AccountInfoDeleteDialogComponent } from 'app/entities/account-info/account-info-delete-dialog.component';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';

describe('Component Tests', () => {
  describe('AccountInfo Management Delete Component', () => {
    let comp: AccountInfoDeleteDialogComponent;
    let fixture: ComponentFixture<AccountInfoDeleteDialogComponent>;
    let service: AccountInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountInfoDeleteDialogComponent]
      })
        .overrideTemplate(AccountInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountInfoService);
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
