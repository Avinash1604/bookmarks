import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Url } from '../../../shared/model/url';
import { UrlService } from 'src/app/shared/service/url.service';
import { GroupUrl } from '../../../shared/model/group-url';
import { GroupService } from 'src/app/shared/service/group.service';
import { Group } from 'src/app/shared/model/group';

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
  urlDetails: Url;
  isUpdated: boolean;
  groupUrl: GroupUrl;
  isGroup: boolean;
  groupId: number;
  constructor(
    private dialogRef: MatDialogRef<CreateLinkComponent>,
    private datePipe: DatePipe,
    private snackBar: MatSnackBar,
    private urlService: UrlService,
    private groupService: GroupService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.urlDetails = data?.urlDetails;
    this.groupUrl = data?.groupUrl;
    this.groupId = data?.groupId;
  }

  ngOnInit(): void {
    this.shortUrlForm = new FormGroup({
      longUrl: new FormControl(),
      expiryDate: new FormControl(),
      title: new FormControl(),
      desc: new FormControl(),
    });
    if (this.urlDetails) {
      this.isUpdated = true;
      this.shortUrlForm.controls.longUrl.setValue(this.urlDetails.longUrl);
      this.shortUrlForm.controls.expiryDate.setValue(
        this.urlDetails.expiryDate
      );
      this.shortUrlForm.controls.title.setValue(this.urlDetails.title);
      this.shortUrlForm.controls.desc.setValue(this.urlDetails.description);
    }
    if (this.groupUrl) {
      this.shortUrlForm.controls.longUrl.setValue(this.groupUrl.longUrl);
      this.shortUrlForm.controls.title.setValue(this.groupUrl.title);
      this.shortUrlForm.controls.desc.setValue(this.groupUrl.description);
    }
  }
  requestShortUrl() {
    if (this.groupId) {
      this.groupRequest();
      return;
    }
    this.loading = true;
    this.shortUrl = '';
    const url = {} as Url;
    url.longUrl = this.shortUrlForm.get('longUrl').value;
    const expiry = this.shortUrlForm.get('expiryDate').value;
    url.description = this.shortUrlForm.get('desc').value;
    url.title = this.shortUrlForm.get('title').value;
    url.bookmarked = true;
    url.expiryDate = expiry
      ? this.datePipe.transform(expiry, 'yyyy-MM-dd')
      : null;
    if (this.isUpdated) {
      url.id = this.urlDetails.id;
      this.edit(url);
      return;
    }
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

  edit(url: Url) {
    this.loading = true;
    this.urlService.updateShortUrl(url).subscribe((data) => {
      this.loading = false;
      this.dialogRef.close(url);
    });
  }

  copy() {
    return this.shortUrl;
  }

  copyImageClick() {
    this.snackBar.open('copied', 'url', {
      duration: 2000,
    });
  }
  closeModal() {
    this.dialogRef.close();
  }

  groupRequest() {
    if (this.groupUrl) {
      this.updateGroup();
    } else {
      this.addUrlToGroup();
    }
  }

  addUrlToGroup() {
    const group = {} as Group;
    group.groupId = this.groupId;
    const _groupUrl = {} as GroupUrl;
    _groupUrl.description = this.shortUrlForm.get('longUrl').value;
    _groupUrl.title = this.shortUrlForm.get('title').value;
    _groupUrl.description = this.shortUrlForm.get('desc').value;
    group.urls = [_groupUrl];
    this.groupService.addUrls(group).subscribe(
      (data) => {},
      (error) => {}
    );
  }

  updateGroup() {
    const group = {} as Group;
    group.groupId = this.groupId;
    this.groupUrl.description = this.shortUrlForm.get('longUrl').value;
    this.groupUrl.title = this.shortUrlForm.get('title').value;
    this.groupUrl.description = this.shortUrlForm.get('desc').value;
    group.urls = [this.groupUrl];
    this.groupService.updateUrl(group).subscribe(
      (data) => {
        this.dialogRef.close(this.groupUrl);
      },
      (error) => {}
    );
  }
}
