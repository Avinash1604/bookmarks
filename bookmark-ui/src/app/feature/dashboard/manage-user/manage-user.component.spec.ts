import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageUserComponent } from './manage-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GroupService } from 'src/app/shared/service/group.service';
import { of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

export class MatDialogMock {
  open() {
    return {
      afterClosed: () => of({ action: true }),
    };
  }
}

export class MatDialogRefMock {
  close() {}
}
describe('ManageUserComponent', () => {
  let component: ManageUserComponent;
  let fixture: ComponentFixture<ManageUserComponent>;
  let groupService: GroupService;
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
            createdOn: null,
          },
        ],
        urls: [],
      },
    ],
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageUserComponent ],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        RouterTestingModule,
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock },
        { provide: MAT_DIALOG_DATA, useValue: {} },
        GroupService,
        {
          provide: ActivatedRoute,
          useValue: { // Mock
            queryParams: of(
              {
                groupId: 1
              }
            )
          }
        }
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageUserComponent);
    component = fixture.componentInstance;
    groupService = TestBed.inject(GroupService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get group by Id', () => {
    spyOn(groupService, 'getGroupsById').and.returnValue(of(group));
    component.getGroupById();
    expect(component.groupDetails).toEqual(group.details[0]);
  });

  it('should admin user edit the role', () => {
    component.editRole(group.details[0].users[0]);
  });

  it('should admin user add the users', () => {
    component.addUser();
  });

  it('should admin user can delete the users', () => {
    spyOn(component, 'getGroupById');
    spyOn(groupService, 'deleteUsers').and.returnValue(of({}));
    component.deleteUser(group.details[0].users[0]);
    expect(component.getGroupById).toHaveBeenCalled();
  });
});
