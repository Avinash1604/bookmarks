import { Component, OnInit, OnDestroy } from '@angular/core';
import { GroupService } from '../../../shared/service/group.service';
import { Group } from '../../../shared/model/group';
import { MatDialog } from '@angular/material/dialog';
import { CreateGroupComponent } from '../create-group/create-group.component';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/model/user';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatSnackBar } from '@angular/material/snack-bar';
import { takeUntil } from 'rxjs/internal/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss'],
})
export class GroupComponent implements OnInit, OnDestroy {
  groupList: Group[];
  loading: boolean;
  private destroy = new Subject<boolean>();
  constructor(
    private groupService: GroupService,
    public dialog: MatDialog,
    private router: Router,
    public clipboard: Clipboard,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.getAllGroups();
    this.newGroupCreatedNotification();
  }

  getAllGroups() {
    this.loading = true;
    this.groupService.getGroups().subscribe((data) => {
      this.loading = false;
      this.groupList = data.details;
    });
  }

  openCard(url: string): void {
    window.open(url, '_blank');
  }

  update(group: Group): void {
    const dialogRef = this.dialog.open(CreateGroupComponent, {
      width: '400px',
      data: { groupDetails: group },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.updateGroup(result);
      }
    });
  }

  newGroupCreatedNotification() {
    this.groupService.getGroupCreatedNotification
      .pipe(takeUntil(this.destroy))
      .subscribe((response) => {
        if (response) {
          this.getAllGroups();
        }
      });
  }

  updateGroup(group: Group) {
    this.groupList
      .filter((data) => data.groupId === group.groupId)
      .map((data) => {
        data.groupName = group.groupName;
        (data.groupContext = group.groupContext),
          (data.groupContextName = group.groupContextName);
      });
  }

  copy(url: string) {
    this.clipboard.copy(url);
    this.snackBar.open('copied', 'url', {
      duration: 2000,
    });
  }

  cardClick(group: Group) {
    this.router.navigate(['/dashboard/group/links'], {
      queryParams: {
        gId: group.groupId,
      },
    });
  }

  delete(id: number) {
    this.loading = true;
    this.groupService.deleteGroup(id).subscribe(
      (data) => {
        this.loading = false;
        this.getAllGroups();
      },
      (err) => {
        this.loading = false;
      }
    );
  }

  userHasAcessToGroup(group: Group) {
    const user = JSON.parse(localStorage.getItem('user')) as User;
    return group.users.filter((data) => data.userId === user.userId).length
      ? true
      : false;
  }

  borderStyleAccessRights(group: Group) {
    return this.userHasAcessToGroup(group)
      ? 'left-border-green'
      : 'left-border-red';
  }

  ngOnDestroy() {
    this.destroy.next(true);
    this.destroy.unsubscribe();
  }
}
