import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FriendService } from 'app/entities/friend/friend.service';
import { IFriend, Friend } from 'app/shared/model/friend.model';
import { FriendType } from 'app/shared/model/enumerations/friend-type.model';

describe('Service Tests', () => {
  describe('Friend Service', () => {
    let injector: TestBed;
    let service: FriendService;
    let httpMock: HttpTestingController;
    let elemDefault: IFriend;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FriendService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Friend('ID', currentDate, FriendType.REQUEST);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateIsFriend: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Friend', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateIsFriend: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateIsFriend: currentDate
          },
          returnedFromService
        );
        service
          .create(new Friend(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Friend', () => {
        const returnedFromService = Object.assign(
          {
            dateIsFriend: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIsFriend: currentDate
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

      it('should return a list of Friend', () => {
        const returnedFromService = Object.assign(
          {
            dateIsFriend: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateIsFriend: currentDate
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

      it('should delete a Friend', () => {
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
