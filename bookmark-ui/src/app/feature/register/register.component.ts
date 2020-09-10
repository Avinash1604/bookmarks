import { Component, OnInit } from '@angular/core';
import { User } from '../../shared/model/user';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
import { UserService } from 'src/app/shared/service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent  {
  user: User = {} as User;
  loading = false;
  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<RegisterComponent>,
    private userService: UserService
  ) {}

  register() {
    this.loading = true;
    this.userService.creatUser(this.user).subscribe(data => {
      this.loading = false;
      this.dialogRef.close();
      this.router.navigate(['/dashboard']);
    }, error => {
      this.loading = false;
    });
  }
}
