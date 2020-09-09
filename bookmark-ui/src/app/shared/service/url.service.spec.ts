import { TestBed } from '@angular/core/testing';

import { UrlService } from './url.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Url } from '../model/url';

describe('UrlService', () => {
  let service: UrlService;
  let httpTestingController: HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
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
    service.requestShortUrl(url).subscribe(
      data => {
        expect(url.longUrl).toEqual('http://test/test/test')
      }
    )
    const req = httpTestingController.expectOne(service.baseUrl);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(url);
  });
});
