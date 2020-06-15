import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AccountInfoService } from 'app/entities/account-info/account-info.service';
import { IAccountInfo, AccountInfo } from 'app/shared/model/account-info.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('AccountInfo Service', () => {
    let injector: TestBed;
    let service: AccountInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccountInfo;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AccountInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AccountInfo('ID', currentDate, Gender.MALE, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dob: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AccountInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dob: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dob: currentDate
          },
          returnedFromService
        );
        service
          .create(new AccountInfo(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AccountInfo', () => {
        const returnedFromService = Object.assign(
          {
            dob: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            age: 1,
            introduce: 'BBBBBB',
            phone: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dob: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AccountInfo', () => {
        const returnedFromService = Object.assign(
          {
            dob: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            age: 1,
            introduce: 'BBBBBB',
            phone: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dob: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AccountInfo', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
