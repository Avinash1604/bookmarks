import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;
  constructor( private router: Router,
               private dialogRef: MatDialogRef<LoginComponent>) { }

  ngOnInit() {

  }

  login() {
    // const user: User[] = this.localStorageService.getLoginData(this.email, this.password);
    // if (user.length > 0) {
    //   localStorage.setItem('loginUser', JSON.stringify(user[0]));
    //   this.router.navigate(['dashboardhome/createSurvey']);
    //   this.dialogRef.close();
    // } else {
    //   alert('Incorrect email or password');
    // }
  }

}
