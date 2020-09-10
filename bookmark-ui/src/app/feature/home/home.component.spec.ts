import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DatePipe } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UrlService } from 'src/app/shared/service/url.service';
import { Url } from 'src/app/shared/model/url';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let urlService: UrlService;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        FormsModule,
      ],
      providers: [DatePipe, UrlService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
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
    spyOn(urlService, 'requestShortUrl').and.returnValue(of(url));
    component.requestShortUrl();
    expect(component.loading).toEqual(false);
  });

});
