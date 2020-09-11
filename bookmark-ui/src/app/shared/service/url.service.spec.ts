import { TestBed } from '@angular/core/testing';

import { UrlService } from './url.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Url } from '../model/url';

describe('UrlService', () => {
  let service: UrlService;
  let httpTestingController: HttpTestingController;

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

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(UrlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a short url', () => {
    const url = {} as Url;
    url.longUrl = 'http://test/test/test';
    url.expiryDate = '2020-12-12';
    service.requestShortUrl(url).subscribe((data) => {
      expect(url.longUrl).toEqual('http://test/test/test');
    });
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(url);
  });

  it('should get all bookmarks', () => {
    service.getBookmarkedShortUrl().subscribe((data) => {
      expect(data).toEqual(bookmarkResponse);
    });
    const req = httpTestingController.expectOne(service.bookmarkUrl);
    expect(req.request.method).toEqual('GET');
    req.flush(bookmarkResponse);
  });

  it('should send the notification when bookmark is created', () => {
    service.getBookmarkAdded.subscribe((data) => {
      expect(data).toEqual(true);
    });
    service.isBookMarkLinkAdded(true);
  });
});
