import { Component, ViewEncapsulation, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Url } from '../../shared/model/url';
import { UrlService } from '../../shared/service/url.service';
import { DatePipe } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [DatePipe]
})
export class HomeComponent implements OnInit {
  title = 'bookmark-ui';
  loading = false;
  shortUrlForm: FormGroup;
  shortUrl: string;
  constructor(
    private urlService: UrlService,
    private datePipe: DatePipe,
    private snackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}

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
    url.expiryDate = expiry
      ? this.datePipe.transform(expiry, 'yyyy-MM-dd')
      : null;
    this.urlService.requestShortUrl(url).subscribe(
      (data: Url) => {
        this.loading = false;
        this.shortUrl = data.shortUrl;
      },
      (error) => {
        this.loading = false;
      }
    );
  }

  openDialogLogin(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '450px',
    });
    dialogRef.afterClosed().subscribe((result) => {});
  }

  openDialogRegister(): void {
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '600px',
    });
    dialogRef.afterClosed().subscribe((result) => {});
  }

  copy() {
    return this.shortUrl;
  }

  copyImageClick() {
    this.snackBar.open('copied', 'url', {
      duration: 2000,
    });
  }
}
