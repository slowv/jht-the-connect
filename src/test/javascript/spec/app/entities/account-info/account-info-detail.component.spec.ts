import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TheconnectTestModule } from '../../../test.module';
import { AccountInfoDetailComponent } from 'app/entities/account-info/account-info-detail.component';
import { AccountInfo } from 'app/shared/model/account-info.model';

describe('Component Tests', () => {
  describe('AccountInfo Management Detail Component', () => {
    let comp: AccountInfoDetailComponent;
    let fixture: ComponentFixture<AccountInfoDetailComponent>;
    const route = ({ data: of({ accountInfo: new AccountInfo('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountInfo).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
