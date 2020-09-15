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
import { ActivatedRoute, Router } from '@angular/router';
import { Group } from 'src/app/shared/model/group';
import { GroupService } from 'src/app/shared/service/group.service';
import { GroupUrl } from 'src/app/shared/model/group-url';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-group-link-card',
  templateUrl: './group-link-card.component.html',
  styleUrls: ['./group-link-card.component.scss'],
})
export class GroupLinkCardComponent implements OnInit, OnDestroy {
  loading = false;
  color: ThemePalette = 'primary';
  groupDetails: Group;
  groupId: number;
  constructor(
    private urlService: UrlService,
    public clipboard: Clipboard,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    public groupService: GroupService,
    public route: ActivatedRoute,
    public router: Router,
    private sanitizer: DomSanitizer
  ) {
    route.queryParams.subscribe((params) => (this.groupId = params.gId));
  }
  bookmarkUrls: Url[];
  private destroy = new Subject<boolean>();
  ngOnInit(): void {
    this.getAllGroups();
    this.newlinkAdded();
  }

  getAllGroups() {
    this.loading = true;
    this.groupService.getGroupsById(this.groupId).subscribe((data) => {
      this.loading = false;
      this.groupDetails = data.details?.[0];
    });
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

  addNewLink(): void {
    const dialogRef = this.dialog.open(CreateLinkComponent, {
      width: '90%',
      data: { groupId: this.groupDetails.groupId },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.addToUrlList(result);
      }
    });
  }

  addToUrlList(url: GroupUrl) {
    this.groupDetails.urls.push(url);
  }

  updateLink(url: GroupUrl): void {
    const dialogRef = this.dialog.open(CreateLinkComponent, {
      width: '90%',
      data: { groupUrl: url, groupId: this.groupDetails.groupId },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.updateUrlList(result);
      }
    });
  }

  updateUrlList(url: GroupUrl) {
    this.groupDetails.urls
      .filter((urlObject) => urlObject.id === url.id)
      .map((urlObj) => {
        urlObj.longUrl = url.longUrl;
        urlObj.description = url.description;
        urlObj.title = url.title;
      });
  }

  delete(groupId, id: number) {
    this.loading = true;
    this.groupService.deleteUrl(groupId, id).subscribe(
      (data) => {
        this.loading = false;
        this.groupDetails.urls = this.groupDetails.urls.filter(
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
        this.delete(this.groupDetails.groupId, data.id);
        break;
      case Operation.EDIT:
        this.updateLink(
          this.groupDetails.urls.filter((obj) => obj.id === data.id)[0]
        );
        break;
    }
  }

  convertUrlToCardModel(url: GroupUrl) {
    const cardModel = {} as CardModel;
    cardModel.description = url.description;
    cardModel.title = url.title;
    cardModel.longUrl = url.longUrl;
    cardModel.shortUrl = url.shortUrl;
    cardModel.id = url.id;
    cardModel.favIcon = this.getFavIcon(url.longUrl);
    cardModel.leftBorderStyle = 'NONE';
    return cardModel;
  }

  addUrlsFromBookmarkSection() {
    this.router.navigate(['/dashboard/all-links']);
  }

  generateDownloadJsonUri() {
    var sJson = JSON.stringify(this.groupDetails.urls);
    var element = document.createElement('a');
    element.setAttribute(
      'href',
      'data:text/json;charset=UTF-8,' + encodeURIComponent(sJson)
    );
    element.setAttribute('download', this.groupDetails.groupName + '.json');
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click(); // simulate click
    document.body.removeChild(element);
  }

  onFileChanged(event) {
    const file = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.readAsText(file, 'UTF-8');
    fileReader.onload = (result) => {
      const urlsDetails: GroupUrl[] = JSON.parse(
        result.target.result as string
      );
      if (urlsDetails) {
        this.loading = true;
        const group: Group = {} as Group;
        group.groupId = this.groupDetails.groupId;
        group.urls = urlsDetails;
        this.groupService.addUrls(group).subscribe(
          (data) => {
            this.loading = false;
            this.getAllUrls
          },
          (err) => {
            this.loading = false;
          }
        );
      }
    };
    fileReader.onerror = (error) => {};
  }
}
