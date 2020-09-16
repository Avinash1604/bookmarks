import { Component, OnInit, Inject } from '@angular/core';
import { GroupService } from 'src/app/shared/service/group.service';
import { GroupListComponent } from '../../link-card/group-list/group-list.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from 'src/app/shared/model/user';
import { UserService } from 'src/app/shared/service/user.service';
import { GroupUser } from 'src/app/shared/model/group-user';
import { Group } from 'src/app/shared/model/group';

@Component({
  selector: 'app-edit-permision',
  templateUrl: './edit-permision.component.html',
  styleUrls: ['./edit-permision.component.scss'],
})
export class EditPermisionComponent implements OnInit {
  role: string;
  loading: boolean;
  isAddUser: boolean;
  userId: number;
  groupUser: GroupUser;
  group: Group;
  users: User[];
  roles = ['ADMIN', 'USER'];
  constructor(
    private dialogRef: MatDialogRef<GroupListComponent>,
    private groupService: GroupService,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.isAddUser = data?.isAddUser;
    this.groupUser = data?.user;
    this.group = data?.group;
    this.role = this.groupUser?.roleName;
  }

  ngOnInit(): void {
    if (this.isAddUser) {
      this.getUsers();
    }
  }

  getUsers() {
    this.loading = true;
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.loading = false;
        this.users = data.userDetails;
      },
      (error) => {
        this.loading = false;
      }
    );
  }
  updateRole() {
    if (this.isAddUser) {
      this.addUser();
      return;
    }
    this.loading = true;
    const groupRequest = {} as Group;
    groupRequest.groupId = this.group.groupId;
    this.groupUser.roleName = this.role;
    groupRequest.users = [this.groupUser];
    this.groupService.updateUsers(groupRequest).subscribe(
      (data) => {
        this.loading = false;
        this.dialogRef.close();
      },
      (error) => {
        this.loading = false;
      }
    );
  }

  addUser() {
    this.loading = true;
    const groupRequest = {} as Group;
    groupRequest.groupId = this.group.groupId;
    groupRequest.users = [this.userDto()];
    this.groupService.addusers(groupRequest).subscribe(
      (data) => {
        this.loading = false;
        this.dialogRef.close(1);
      },
      (error) => {
        this.loading = false;
      }
    );
  }

  userDto(): GroupUser {
    const selectedUser = this.users.filter(
      (data) => data.userId === this.userId
    )[0];
    const groupUserRequest = {} as GroupUser;
    groupUserRequest.userName = selectedUser.userName;
    groupUserRequest.email = selectedUser.email;
    groupUserRequest.userId = selectedUser.userId;
    groupUserRequest.roleName = this.role;
    return groupUserRequest;
  }

  closeModal() {
    this.dialogRef.close();
  }
}
