import { Component, OnInit } from '@angular/core';
import { GroupService } from '../../../shared/service/group.service';
import { Group } from '../../../shared/model/group';
import { MatDialog } from '@angular/material/dialog';
import { CreateGroupComponent } from '../create-group/create-group.component';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss'],
})
export class GroupComponent implements OnInit {
  groupList: Group[];
  loading: boolean;
  constructor(private groupService: GroupService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.getAllGroups();
  }

  getAllGroups() {
    this.loading = true;
    this.groupService.getGroups().subscribe((data) => {
      this.loading = false;
      this.groupList = data.details;
    });
  }

  openCard(): void {}
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

  updateGroup(group: Group) {
    this.groupList
      .filter((data) => data.groupId === group.groupId)
      .map((data) => {
        data.groupName = group.groupName;
        (data.groupContext = group.groupContext),
          (data.groupContextName = group.groupContextName);
      });
  }

  copy() {

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
}
