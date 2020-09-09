import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { User } from '../model/user';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should creat a user', () => {
    const user = {} as User;
    user.userName = 'userName';
    user.password = 'password';
    user.email = 'email@email.com';
    service.creatUser(user).subscribe((data) => {
      expect(data.email).toEqual('email@email.com');
    });
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(user);
  });

  it('should get a user by credentials', () => {
    const user = {} as User;
    user.userName = 'userName';
    user.password = 'password';
    user.email = 'email@email.com';
    service.getUser(user).subscribe((data) => {
      expect(data.email).toEqual('email@email.com');
    });
    const req = httpTestingController.expectOne(service.baseUrl+service.getUserByCredentials);
    expect(req.request.method).toEqual('GET');
    expect(req.request.body).toEqual(user);
  });
});
