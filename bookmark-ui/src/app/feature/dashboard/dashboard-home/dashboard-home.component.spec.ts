import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardHomeComponent } from './dashboard-home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { UrlService } from 'src/app/shared/service/url.service';
import { GroupService } from 'src/app/shared/service/group.service';
import { of } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

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

const router = {
  navigate: jasmine.createSpy('navigate'),
};


describe('DashboardHomeComponent', () => {
  let component: DashboardHomeComponent;
  let fixture: ComponentFixture<DashboardHomeComponent>;
  let urlService: UrlService;
  let groupService: GroupService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardHomeComponent ],
      imports: [BrowserAnimationsModule, MaterialModule, RouterTestingModule],
      providers: [UrlService, GroupService,
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock}
      ],

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardHomeComponent);
    component = fixture.componentInstance;
    urlService = TestBed.inject(UrlService);
    groupService = TestBed.inject(GroupService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should send notification when bookmark is created', () => {
    component.createLinkLogin();
  });

  it('should send notification when group is created', () => {
    component.createGroup();
  });

  it('should allow user to login', () => {
    component.openDialogLogin();
  });

  it('should allow user to register', () => {
    component.openDialogRegister();
  });
});
