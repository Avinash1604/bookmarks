import { Component, OnInit, OnDestroy } from '@angular/core';
import { UrlService } from 'src/app/shared/service/url.service';
import { Url } from 'src/app/shared/model/url';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/internal/operators';
import { MatDialog } from '@angular/material/dialog';
import { CreateLinkComponent } from '../create-link/create-link.component';
import { ThemePalette } from '@angular/material/core';

@Component({
  selector: 'app-link-card',
  templateUrl: './link-card.component.html',
  styleUrls: ['./link-card.component.scss'],
})
export class LinkCardComponent implements OnInit, OnDestroy {
  loading = false;
  color: ThemePalette = 'primary';
  constructor(
    private urlService: UrlService,
    public clipboard: Clipboard,
    private snackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}
  bookmarkUrls: Url[];
  private destroy = new Subject<boolean>();
  ngOnInit(): void {
    this.getAllUrls();
    this.newlinkAdded();
  }

  getAllUrls() {
    this.loading = true;
    return this.urlService.getBookmarkedShortUrl().subscribe(
      (data) => {
        this.loading = false;
        this.bookmarkUrls = data.details;
      },
      (error) => {
        this.loading = false;
      }
    );
  }

  getFavIcon(url: string) {
    const longUrl = new URL(url);
    let basepath = longUrl.protocol + '//' + longUrl.hostname;
    if (longUrl.port) {
      basepath = basepath + ':' + longUrl.port;
    }
    return basepath + '/favicon.ico';
  }

  copy(url: string) {
    this.clipboard.copy(url);
    this.snackBar.open('copied', 'url', {
      duration: 2000,
    });
  }

  openUrlOnCardClick(url: string) {
    window.open(url, '_blank');
  }

  newlinkAdded() {
    this.urlService.getBookmarkAdded
      .pipe(takeUntil(this.destroy))
      .subscribe((response) => {
        if (response) {
          this.getAllUrls();
        }
      });
  }

  updateLink(url: Url): void {
    const dialogRef = this.dialog.open(CreateLinkComponent, {
      width: '90%',
      data: { urlDetails: url },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.updateUrlList(url);
      }
    });
  }

  updateUrlList(url: Url) {
    this.bookmarkUrls
      .filter((urlObject) => urlObject.id === urlObject.id)
      .map((urlObj) => {
        urlObj.longUrl = url.longUrl;
        urlObj.description = url.description;
        urlObj.expiryDate = url.expiryDate;
        urlObj.title = urlObj.title;
      });
  }

  delete(id: number) {
    this.loading = true;
    this.urlService.deleteShortUrl(id).subscribe(
      (data) => {
        this.loading = false;
        this.bookmarkUrls = this.bookmarkUrls.filter(
          (urlObject) => urlObject.id !== id
        );
      },
      (err) => {
        this.loading = false;
      }
    );
  }

  ngOnDestroy() {
    this.destroy.next(true);
    this.destroy.unsubscribe();
  }

  borderStyleExpired(dateStr: string) {
    return new Date(dateStr) >= new Date()
      ? 'left-border-green'
      : 'left-border-red';
  }
}
