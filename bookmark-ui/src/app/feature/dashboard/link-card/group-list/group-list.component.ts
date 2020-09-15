import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GroupService } from 'src/app/shared/service/group.service';
import { Group } from 'src/app/shared/model/group';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.scss'],
})
export class GroupListComponent implements OnInit {
  loading: boolean;
  groupList: Group[];
  constructor(
    private dialogRef: MatDialogRef<GroupListComponent>,
    private groupService: GroupService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

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

  closeModal(){
    this.dialogRef.close();
  }

  groupClick(group){
    this.dialogRef.close(group);
  }
}
