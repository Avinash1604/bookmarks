import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Group } from '../../../shared/model/group';
import { GroupUser } from '../../../shared/model/group-user';
import { GroupService } from '../../../shared/service/group.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.scss'],
})
export class CreateGroupComponent implements OnInit {
  isUpdated: boolean;
  shortUrlForm: FormGroup;
  loading: boolean;
  groupDetails: Group;
  groupContext = ['NONE', 'USER', 'TRIBE', 'APPLICATION'];
  constructor(
    private dialogRef: MatDialogRef<CreateGroupComponent>,
    private groupService: GroupService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (this.data){
      this.groupDetails = data.groupDetails;
    }
  }

  ngOnInit(): void {
    this.shortUrlForm = new FormGroup({
      groupName: new FormControl(null, Validators.required),
      groupContext: new FormControl(null, Validators.required),
      groupContextName: new FormControl(null, Validators.required),
    });
    if (this.groupDetails){
      this.isUpdated = true;
      this.shortUrlForm.controls.groupName.setValue(this.groupDetails.groupName);
      this.shortUrlForm.controls.groupContext.setValue(this.groupDetails.groupContext);
      this.shortUrlForm.controls.groupContextName.setValue(this.groupDetails.groupContextName);
    }

  }

  closeModal() {
    this.dialogRef.close();
  }

  createGroup() {
    const group = {} as Group;
    this.loading = true;
    group.groupName = this.shortUrlForm.get('groupName').value;
    group.groupContext = this.shortUrlForm.get('groupContext').value;
    group.groupContextName = this.shortUrlForm.get('groupContextName').value;
    if (this.isUpdated){
      group.groupId = this.groupDetails.groupId;
      this.updateGroup(group);
      return;
    }
    group.users = [this.getUser()];
    this.groupService.createGroup(group).subscribe(
      data => {
        this.loading = false;
        this.dialogRef.close();
        alert('Group successfully created');
      }, error => {
        this.loading = false;
      }
    );
  }

  getUser() {
    const user = JSON.parse(localStorage.getItem('user'));
    const groupUser = {} as GroupUser;
    groupUser.email = user?.email;
    groupUser.roleName = 'ADMIN';
    groupUser.userName = user?.userName;
    groupUser.userId = user?.userId;
    return groupUser;
  }

  updateGroup(group: Group){
    this.groupService.updateGroup(group).subscribe(
      data => {
        this.loading = false;
        alert('Group updated successfully');
        this.dialogRef.close(group);
      }, error => {
        this.loading = false;
      }
    );
  }
}
