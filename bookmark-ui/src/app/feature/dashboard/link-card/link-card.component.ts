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
import {
  Operation,
  CardModel,
} from 'src/app/shared/bookmark-card-layout/bookmark-card-layout.component';
import { GroupListComponent } from './group-list/group-list.component';
import { GroupService } from 'src/app/shared/service/group.service';
import { Group } from 'src/app/shared/model/group';
import { GroupUrl } from 'src/app/shared/model/group-url';

@Component({
  selector: 'app-link-card',
  templateUrl: './link-card.component.html',
  styleUrls: ['./link-card.component.scss'],
})
export class LinkCardComponent implements OnInit, OnDestroy {
  loading = false;
  color: ThemePalette = 'primary';
  bookmarkUrls: Url[];
  selectedUrls: Url[] = [];

  private destroy = new Subject<boolean>();
  constructor(
    private urlService: UrlService,
    public clipboard: Clipboard,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    public groupService: GroupService
  ) {}
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
        this.updateUrlList(result);
      }
    });
  }

  updateUrlList(url: Url) {
    this.bookmarkUrls
      .filter((urlObject) => urlObject.id === url.id)
      .map((urlObj) => {
        urlObj.longUrl = url.longUrl;
        urlObj.description = url.description;
        urlObj.expiryDate = url.expiryDate;
        urlObj.title = url.title;
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

  isDateExpired(date: string) {
    const now = new Date();
    const linkExpiryDate = new Date(date);
    now.setHours(0, 0, 0, 0);
    linkExpiryDate.setHours(0, 0, 0, 0);
    return linkExpiryDate >= now;
  }

  borderStyleExpired(dateStr: string) {
    return this.isDateExpired(dateStr)
      ? 'left-border-green'
      : 'left-border-red';
  }

  cardOperation(event) {
    const operation = event.operation;
    const data = event.model;
    switch (operation) {
      case Operation.COPY:
        this.copy(data.shortUrl);
        break;
      case Operation.CARD_CLICK:
        this.openUrlOnCardClick(data.longUrl);
        break;
      case Operation.DELETE:
        this.delete(data.id);
        break;
      case Operation.EDIT:
        this.updateLink(
          this.bookmarkUrls.filter((obj) => obj.id === data.id)[0]
        );
        break;
      case Operation.CHECKBOX:
        this.addOrRemoveLinks(data);
    }
  }

  addOrRemoveLinks(cardModel: CardModel) {
    if (cardModel.selected) {
      this.selectedUrls = this.selectedUrls.concat(
        this.bookmarkUrls.filter((object) => object.id === cardModel.id)
      );
    } else {
      this.selectedUrls = this.selectedUrls.filter(
        (object) => object.id !== cardModel.id
      );
    }
  }

  convertUrlToCardModel(url: Url) {
    const cardModel = {} as CardModel;
    cardModel.description = url.description;
    cardModel.title = url.title;
    cardModel.longUrl = url.longUrl;
    cardModel.shortUrl = url.shortUrl;
    cardModel.id = url.id;
    cardModel.favIcon = this.getFavIcon(url.longUrl);
    cardModel.leftBorderStyle = this.borderStyleExpired(url.expiryDate);
    cardModel.selectionRequired = true;
    return cardModel;
  }

  movToGroup() {
    if (!this.isUserLogin()){
      alert('Please Log In to add to group');
      return;
    }
    const dialogRef = this.dialog.open(GroupListComponent, {
      width: '600px',
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loading = true;
        const group = {} as Group;
        group.groupId = result.groupId;
        group.urls = this.selectedUrls.map((data) => {
          const groupUrl = {} as GroupUrl;
          groupUrl.longUrl = data.longUrl;
          groupUrl.description = data.description;
          groupUrl.title = data.title;
          return groupUrl;
        });
        this.groupService.addUrls(group).subscribe(
          (data) => {
            this.loading = false;
            alert('Urls added successfully to a Group');
          },
          (error) => {
            this.loading = false;
          }
        );
      }
    });
  }

  isUserLogin() {
    if (localStorage.getItem('user') === null){
       return false;
    }
    return true;
 }
}
