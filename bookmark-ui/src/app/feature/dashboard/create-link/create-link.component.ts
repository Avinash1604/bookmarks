import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Url } from '../../../shared/model/url';
import { UrlService } from 'src/app/shared/service/url.service';

@Component({
  selector: 'app-create-link',
  templateUrl: './create-link.component.html',
  styleUrls: ['./create-link.component.scss'],
  providers: [DatePipe],
})
export class CreateLinkComponent implements OnInit {
  shortUrl: string;
  shortUrlForm: FormGroup;
  loading = false;
  constructor(
    private dialogRef: MatDialogRef<CreateLinkComponent>,
    private datePipe: DatePipe,
    private snackBar: MatSnackBar,
    private urlService: UrlService
  ) {}

  ngOnInit(): void {
    this.shortUrlForm = new FormGroup({
      longUrl: new FormControl(),
      expiryDate: new FormControl(),
      title: new FormControl(),
      desc: new FormControl(),
    });
  }
  requestShortUrl() {
    this.loading = true;
    this.shortUrl = '';
    const url = {} as Url;
    url.longUrl = this.shortUrlForm.get('longUrl').value;
    const expiry = this.shortUrlForm.get('expiryDate').value;
    url.description =  this.shortUrlForm.get('desc').value;
    url.title = this.shortUrlForm.get('title').value;
    url.isBookmark = true;
    url.expiryDate = expiry
      ? this.datePipe.transform(expiry, 'yyyy-MM-dd')
      : null;
    this.urlService.requestShortUrl(url).subscribe(
      (data: Url) => {
        this.loading = false;
        this.dialogRef.close(1);
      },
      (error) => {
        this.loading = false;
        alert('server not responding');
      }
    );

  }

  copy() {
    return this.shortUrl;
  }

  copyImageClick() {
    this.snackBar.open('copied', 'url', {
      duration: 2000,
    });
  }
  closeModal(){
    this.dialogRef.close();
  }
}