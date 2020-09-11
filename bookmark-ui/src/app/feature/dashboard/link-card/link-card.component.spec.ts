import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkCardComponent } from './link-card.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { MaterialModule } from 'src/app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UrlService } from 'src/app/shared/service/url.service';
import { DatePipe } from '@angular/common';
import { of } from 'rxjs';
import { url } from 'inspector';

describe('LinkCardComponent', () => {
  let component: LinkCardComponent;
  let fixture: ComponentFixture<LinkCardComponent>;
  let urlService: UrlService;

  const bookmarkResponse = {
    details: [
      {
        longUrl:
          'https://stackoverflow.com/questions/10282939/how-to-get-favicons-url-from-a-generic-webpage-in-javascript',
        id: 114,
        expiryDate: '2020-09-14',
        shortUrl: 'https://bookmarks-tiny.herokuapp.com/b0',
        createdOn: '2020-09-11T11:45:46.506037',
        title: 'Stack overflow',
        description: 'Testing the stack overflow link',
        bookmarked: true,
      },
      {
        longUrl: 'https://engineering-stream-hackathon.github.io/challenge/#/',
        id: 115,
        expiryDate: '2020-09-02',
        shortUrl: 'https://bookmarks-tiny.herokuapp.com/b1',
        createdOn: '2020-09-11T07:17:22.881053',
        title: 'Hackathon url',
        description: 'hackthon url bookmark  ',
        bookmarked: true,
      },
    ],
  };
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LinkCardComponent],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        RouterTestingModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [UrlService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkCardComponent);
    component = fixture.componentInstance;
    urlService = TestBed.inject(UrlService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get all bookmarked urls', () => {
    spyOn(urlService, 'getBookmarkedShortUrl').and.returnValue(
      of(bookmarkResponse)
    );
    component.getAllUrls();
    expect(component.bookmarkUrls).toEqual(bookmarkResponse.details);
  });

  it('should get a border color depending on expiry or non expiry date', () => {
    expect(component.borderStyleExpired('2020-06-20')).toEqual(
      'left-border-red'
    );
    expect(component.borderStyleExpired('2025-06-20')).toEqual(
      'left-border-green'
    );
  });

  it('should get a notification when new link is added', () => {
    urlService.isBookMarkLinkAdded(true);
    component.newlinkAdded();
  });
});
