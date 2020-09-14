import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupComponent } from './create-group.component';
import { of } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GroupService } from 'src/app/shared/service/group.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from 'src/app/shared/model/user';

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

describe('CreateGroupComponent', () => {
  let component: CreateGroupComponent;
  let fixture: ComponentFixture<CreateGroupComponent>;
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
      declarations: [CreateGroupComponent],
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
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupComponent);
    const user = {} as User;
    user.userName = 'userName';
    user.userId = 1;
    user.password = 'password';
    user.email = 'email';
    localStorage.setItem('user', JSON.stringify(user));
    component = fixture.componentInstance;
    groupService = TestBed.inject(GroupService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a group', () => {
    spyOn(groupService, 'createGroup').and.returnValue(of(group.details[0]));
    component.createGroup();
    expect(component.loading).toEqual(false);
  });

  it('should update a group', () => {
    component.groupDetails = group.details[0];
    spyOn(groupService, 'updateGroup').and.returnValue(of('data'));
    component.ngOnInit();
    component.createGroup();
    expect(component.isUpdated).toEqual(true);
    expect(component.loading).toEqual(false);
  });
});
