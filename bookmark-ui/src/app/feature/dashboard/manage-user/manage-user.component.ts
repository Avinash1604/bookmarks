import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { EditPermisionComponent } from './edit-permision/edit-permision.component';
import { GroupService } from 'src/app/shared/service/group.service';
import { Group } from 'src/app/shared/model/group';
import { GroupUser } from 'src/app/shared/model/group-user';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss'],
})
export class ManageUserComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['usernamne', 'email', 'role', 'operations'];
  dataSource: MatTableDataSource<GroupUser>;
  loading: boolean;
  groupId: number;
  groupDetails: Group;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(
    public dialog: MatDialog,
    public groupService: GroupService,
    public route: ActivatedRoute,
    public location: Location
  ) {
    route.queryParams.subscribe((params) => (this.groupId = params.gId));
  }

  ngOnInit(): void {
    this.getGroupById();
  }

  ngAfterViewInit() {}

  getGroupById() {
    this.loading = true;
    this.groupService.getGroupsById(this.groupId).subscribe((data) => {
      this.loading = false;
      this.groupDetails = data.details?.[0];
      this.dataSource = new MatTableDataSource(this.groupDetails.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  editRole(user: GroupUser): void {
    const dialogRef = this.dialog.open(EditPermisionComponent, {
      width: '300px',
      data: { user, group: this.groupDetails },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getGroupById();
      }
    });
  }

  deleteUser(user: GroupUser) {
    this.loading = true;
    this.groupService.deleteUsers(this.groupId, user.id).subscribe(
      (data) => {
        this.loading = false;
        this.getGroupById();
      },
      (error) => {
        this.loading = false;
      }
    );
  }

  back() {
    this.location.back();
  }

  addUser() {
    const dialogRef = this.dialog.open(EditPermisionComponent, {
      width: '500px',
      data: { isAddUser: true, group: this.groupDetails },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getGroupById();
      }
    });
  }
}
