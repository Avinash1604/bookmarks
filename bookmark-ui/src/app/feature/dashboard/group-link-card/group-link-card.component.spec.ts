import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupLinkCardComponent } from './group-link-card.component';
import { of } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { GroupService } from 'src/app/shared/service/group.service';
import { Group } from 'src/app/shared/model/group';
import { Operation } from 'src/app/shared/bookmark-card-layout/bookmark-card-layout.component';

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

describe('GroupLinkCardComponent', () => {
  let component: GroupLinkCardComponent;
  let fixture: ComponentFixture<GroupLinkCardComponent>;
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
        urls: [
          {
            id: 2,
            groupId: 1,
            title: 'test1',
            longUrl: 'https://angular.io/guide/transition-and-triggers',
            description: 'angular website'
          },
        ],
      },
    ],
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GroupLinkCardComponent],
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
    groupService = TestBed.inject(GroupService);
    spyOn(groupService, 'getGroupsById').and.returnValue(of(group));
    fixture = TestBed.createComponent(GroupLinkCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a cardModel for url model', () => {
    const cardModel = component.convertUrlToCardModel(
      component.groupDetails.urls[0]
    );
    expect(cardModel.id).toEqual(2);
  });

  it('should copy object when clicks on copy', () => {
    spyOn(component.clipboard, 'copy');
    component.copy('test');
    expect(component.clipboard.copy).toHaveBeenCalledWith('test');
  });

  it('should get a fav ion url for the given url', () => {
    expect(component.getFavIcon('https://stackoverflow.com/questions')).toEqual(
      'https://stackoverflow.com/favicon.ico'
    );
  });

  it('should open new window and redirect to web app when user clicks on card', () => {
    spyOn(window, 'open');
    component.openUrlOnCardClick('http://localhost');
    expect(window.open).toHaveBeenCalledWith('http://localhost', '_blank');
  });

  it('should all card layout ouput events should work', () => {
    spyOn(component, 'copy');
    const cardModel = component.convertUrlToCardModel(
      component.groupDetails.urls[0]
    );
    component.cardOperation({ model: cardModel, operation: Operation.COPY });
    expect(component.copy).toHaveBeenCalled();
    spyOn(component, 'updateLink');
    component.cardOperation({ model: cardModel, operation: Operation.EDIT });
    expect(component.updateLink).toHaveBeenCalled();
    spyOn(component, 'delete');
    component.cardOperation({ model: cardModel, operation: Operation.DELETE });
    expect(component.delete).toHaveBeenCalled();
    spyOn(component, 'openUrlOnCardClick');
    component.cardOperation({
      model: cardModel,
      operation: Operation.CARD_CLICK,
    });
    expect(component.openUrlOnCardClick).toHaveBeenCalled();
  });


});
