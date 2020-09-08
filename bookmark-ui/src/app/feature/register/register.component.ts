import { Component, OnInit } from '@angular/core';
import { User } from '../../shared/model/user';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  user: User = {} as User;

  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<RegisterComponent>
  ) {}

  register() {
    this.dialogRef.close();
    this.router.navigate(['dashboardhome/createSurvey']);
  }

  ngOnInit(): void {}
}
