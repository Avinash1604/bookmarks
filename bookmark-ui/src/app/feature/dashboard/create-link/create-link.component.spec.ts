import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateLinkComponent } from './create-link.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/shared/material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService } from 'src/app/shared/service/user.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Url } from 'src/app/shared/model/url';
import { UrlService } from 'src/app/shared/service/url.service';

const router = {
  navigate: jasmine.createSpy('navigate'),
};

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

describe('CreateLinkComponent', () => {
  let component: CreateLinkComponent;
  let fixture: ComponentFixture<CreateLinkComponent>;
  let urlService: UrlService;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateLinkComponent],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock },
        { provide: Router, useValue: router },
        UrlService,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateLinkComponent);
    component = fixture.componentInstance;
    urlService = TestBed.inject(UrlService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a short url', () => {
    const url = {} as Url;
    url.longUrl = 'http://test/test/test';
    url.expiryDate = '2020-12-12';
    url.title = 'title';
    url.description = 'desc';
    url.bookmarked = true;
    spyOn(urlService, 'requestShortUrl').and.returnValue(of(url));
    component.requestShortUrl();
    expect(component.loading).toEqual(false);
  });

});
