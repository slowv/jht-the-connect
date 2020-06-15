import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TheconnectTestModule } from '../../../test.module';
import { AccountTCDetailComponent } from 'app/entities/account-tc/account-tc-detail.component';
import { AccountTC } from 'app/shared/model/account-tc.model';

describe('Component Tests', () => {
  describe('AccountTC Management Detail Component', () => {
    let comp: AccountTCDetailComponent;
    let fixture: ComponentFixture<AccountTCDetailComponent>;
    const route = ({ data: of({ accountTC: new AccountTC('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TheconnectTestModule],
        declarations: [AccountTCDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountTCDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountTCDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountTC).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
