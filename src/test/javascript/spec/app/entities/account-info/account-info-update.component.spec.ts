import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TheconnectTestModule } from '../../../test.module';
import { AccountInfoUpdateComponent } from 'app/entities/account-info/account-info-update.component';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';
import { AccountInfo } from 'app/shared/model/account-info.model';

describe('Component Tests', () => {
  describe('AccountInfo Management Update Component', () => {
    let comp: AccountInfoUpdateComponent;
    let fixture: ComponentFixture<AccountInfoUpdateComponent>;
    let service: AccountInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountInfo('123');
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
        const entity = new AccountInfo();
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
