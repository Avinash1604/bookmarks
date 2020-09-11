import { Component, OnInit, OnDestroy } from '@angular/core';
import { UrlService } from 'src/app/shared/service/url.service';
import { Url } from 'src/app/shared/model/url';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/internal/operators';

@Component({
  selector: 'app-link-card',
  templateUrl: './link-card.component.html',
  styleUrls: ['./link-card.component.scss'],
})
export class LinkCardComponent implements OnInit, OnDestroy {
  item = [1, 2, 3, 4, 5, 6, 7];
  constructor(
    private urlService: UrlService,
    public clipboard: Clipboard,
    private snackBar: MatSnackBar
  ) {}
  bookmarkUrls: Url[];
  private destroy = new Subject<boolean>();
  ngOnInit(): void {
    this.getAllUrls();
    this.newlinkAdded();
  }

  getAllUrls() {
    return this.urlService.getBookmarkedShortUrl().subscribe(
      (data) => {
        this.bookmarkUrls = data.details;
      },
      (error) => {}
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
