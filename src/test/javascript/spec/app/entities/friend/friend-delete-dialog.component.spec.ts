import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TheconnectTestModule } from '../../../test.module';
import { FriendDeleteDialogComponent } from 'app/entities/friend/friend-delete-dialog.component';
import { FriendService } from 'app/entities/friend/friend.service';

describe('Component Tests', () => {
  describe('Friend Management Delete Component', () => {
    let comp: FriendDeleteDialogComponent;
    let fixture: ComponentFixture<FriendDeleteDialogComponent>;
    let service: FriendService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [FriendDeleteDialogComponent]
      })
        .overrideTemplate(FriendDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FriendDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FriendService);
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
