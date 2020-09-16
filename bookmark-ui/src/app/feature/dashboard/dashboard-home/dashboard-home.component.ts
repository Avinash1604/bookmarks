import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateLinkComponent } from '../create-link/create-link.component';
import { UrlService } from 'src/app/shared/service/url.service';
import { CreateGroupComponent } from '../create-group/create-group.component';
import { GroupService } from 'src/app/shared/service/group.service';

@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.scss'],
})
export class DashboardHomeComponent implements OnInit {
  screenWidth: number;
  panelOpenState = false;
  getUserInfo;
  constructor(
    private route: Router,
    public dialog: MatDialog,
    private urlService: UrlService,
    private groupService: GroupService
  ) {
    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      this.screenWidth = window.innerWidth;
    };
  }

  ngOnInit() {}

  createLinkLogin(): void {
    const dialogRef = this.dialog.open(CreateLinkComponent, {
      width: '90%',
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.urlService.isBookMarkLinkAdded(true);
      }
    });
  }

  createGroup(): void {
    const dialogRef = this.dialog.open(CreateGroupComponent, {
      width: '400px',
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.groupService.isGroupCreated(true);
      }
    });
  }
}
