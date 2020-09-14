import { TestBed } from '@angular/core/testing';

import { GroupService } from './group.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Group } from '../model/group';

describe('GroupService', () => {
  let service: GroupService;
  let httpTestingController: HttpTestingController;

  const group = {
    details: [
       {
          groupId: 1,
          groupName: 'ATOM',
          groupContextName: 'user',
          groupContext: 'NONE',
          groupUrl: null,
          createdOn: '2020-09-13T19:35:57.227394',
          users: [
             {
                id: null,
                groupId: 1,
                userId: 1,
                userName: 'user1',
                email: 'ueser1@gmail.com',
                roleName: 'ADMIN',
                createdOn: null
             }
          ],
          urls: [

          ]
       }
    ]
 };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(GroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should create a group', () => {

    service.createGroup(group.details[0]).subscribe((data) => {
      expect(group.details[0].groupName).toEqual('ATOM');
    });
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(group.details[0]);
  });

  it('should get all group', () => {
    service.getGroups().subscribe((data) => {
      expect(data).toEqual(group);
    });
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('GET');
    req.flush(group);
  });


  it('should update a group', () => {

    service.updateGroup(group.details[0]).subscribe((data) => {
    });
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual(group.details[0]);
  });

  it('should delete a short url', () => {
    service.deleteGroup(1).subscribe((data) => {
    });
    const req = httpTestingController.expectOne(service.baseUrl + '/1');
    expect(req.request.method).toEqual('DELETE');
  });
});
