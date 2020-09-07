import { Component, ViewEncapsulation, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Url } from './shared/model/url';
import { UrlService } from './shared/service/url.service';
import { DatePipe } from '@angular/common';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [DatePipe]
})
export class AppComponent implements OnInit {
  title = 'bookmark-ui';
  loading = false;
  shortUrlForm: FormGroup;
  shortUrl: string = 'test';
  constructor(private urlService: UrlService, private datePipe: DatePipe, private _snackBar: MatSnackBar) {}

  ngOnInit() {
    this.shortUrlForm = new FormGroup({
      longUrl: new FormControl(),
      expiryDate: new FormControl(),
    });
  }

  requestShortUrl() {
    this.loading = true;
    this.shortUrl = '';
    const url = {} as Url;
    url.longUrl = this.shortUrlForm.get('longUrl').value;
    const expiry = this.shortUrlForm.get('expiryDate').value;
    url.expiryDate = expiry?this.datePipe.transform(expiry, 'yyyy-MM-dd'):null;
    this.urlService.requestShortUrl(url).subscribe(
      (data: Url) => {
        this.loading = false;
        this.shortUrl = data.shortUrl;
      },
      (error) => {
        this.loading = false;
        console.log("error"+error)
      }
    );
  }

  copy() {
    return this.shortUrl;
  }

  copyImageClick(){
    this._snackBar.open('copied','url', {
      duration: 2000,
    });
  }
}
