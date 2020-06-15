import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TheconnectTestModule } from '../../../test.module';
import { AccountTCUpdateComponent } from 'app/entities/account-tc/account-tc-update.component';
import { AccountTCService } from 'app/entities/account-tc/account-tc.service';
import { AccountTC } from 'app/shared/model/account-tc.model';

describe('Component Tests', () => {
  describe('AccountTC Management Update Component', () => {
    let comp: AccountTCUpdateComponent;
    let fixture: ComponentFixture<AccountTCUpdateComponent>;
    let service: AccountTCService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountTCUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountTCUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountTCUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountTCService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountTC('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountTC();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
