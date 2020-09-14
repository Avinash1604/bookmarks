import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { MaterialModule } from 'src/app/shared/material.module';
import { GroupService } from 'src/app/shared/service/group.service';
import { GroupComponent } from './group.component';

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

describe('GroupComponent', () => {
  let component: GroupComponent;
  let fixture: ComponentFixture<GroupComponent>;
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
      declarations: [GroupComponent],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [
        GroupService,
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupComponent);
    component = fixture.componentInstance;
    groupService = TestBed.inject(GroupService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get all group', () => {
    spyOn(groupService, 'getGroups').and.returnValue(of(group));
    component.getAllGroups();
    expect(component.groupList).toEqual(group.details);
  });

  it('should delete a group', () => {
    component.groupList = group.details;
    spyOn(groupService, 'deleteGroup').and.returnValue(of('success'));
    component.delete(1);
    expect(groupService.deleteGroup).toHaveBeenCalledWith(1);
  });

  it('should update the url data', () => {
    component.groupList = group.details;
    component.updateGroup(group.details[0]);
  });
});
