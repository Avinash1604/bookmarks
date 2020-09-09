import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
import { UserService } from 'src/app/shared/service/user.service';
import { User } from '../../shared/model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<LoginComponent>,
    private userService: UserService
  ) {}

  ngOnInit() {}

  login() {
    const user = {} as User;
    user.email = this.email;
    user.password = this.password;
    this.userService.getUser(user).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        alert('Incorrect email or password');
      }
    );
  }
}
