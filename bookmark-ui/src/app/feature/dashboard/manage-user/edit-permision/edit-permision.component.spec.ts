import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPermisionComponent } from './edit-permision.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { GroupService } from 'src/app/shared/service/group.service';
import { UserService } from 'src/app/shared/service/user.service';
import { User } from 'src/app/shared/model/user';
import { Group } from 'src/app/shared/model/group';
import { GroupUser } from 'src/app/shared/model/group-user';

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

describe('EditPermisionComponent', () => {
  let component: EditPermisionComponent;
  let fixture: ComponentFixture<EditPermisionComponent>;
  let groupService: GroupService;
  let userService: UserService;

  const user = {
    userDetails: [
      {
        email: 'test123@gmail.com',
        userName: 'test123',
        userId: 4
      }
    ]
  };


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditPermisionComponent],
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
        UserService
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPermisionComponent);
    component = fixture.componentInstance;
    groupService = TestBed.inject(GroupService);
    userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get users', () => {
    spyOn(userService, 'getAllUsers').and.returnValue(of(user));
    component.getUsers();
    expect(component.users).toEqual(user.userDetails as User[]);
  });

  it('should add user', () => {
    component.users = user.userDetails as User[];
    component.role = 'ADMIN';
    component.group = {
      groupId: 1,
      groupName: 'test',
      groupContext: 'USER',
      groupUrl: '',
      groupContextName: 'name',
      urls: [],
      users: []
    } as Group;
    component.userId = 4;
    component.isAddUser = true;
    spyOn(groupService, 'addusers').and.returnValue(of({}));
    component.updateRole();
    expect(groupService.addusers).toHaveBeenCalled();
  });

  it('should update user role', () => {
    component.users = user.userDetails as User[];
    component.role = 'ADMIN';
    component.groupUser = {
      email: 'test123@gmail.com',
        userName: 'test123',
        userId: 4,
        id: 1
    } as GroupUser;
    component.group = {
      groupId: 1,
      groupName: 'test',
      groupContext: 'USER',
      groupUrl: '',
      groupContextName: 'name',
      urls: [],
      users: []
    } as Group;
    component.userId = 4;
    spyOn(groupService, 'updateUsers').and.returnValue(of({}));
    component.updateRole();
    expect(groupService.updateUsers).toHaveBeenCalled();
  });

});
