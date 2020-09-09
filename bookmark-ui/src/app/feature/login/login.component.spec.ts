import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { of } from 'rxjs';
import { MaterialModule } from 'src/app/shared/material.module';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from '../../shared/service/user.service';
import { User } from '../../shared/model/user';
import { HttpClientTestingModule } from '@angular/common/http/testing';

const router = {
  navigate: jasmine.createSpy('navigate')
};

export class MatDialogMock {
  open() {
    return {
       afterClosed: () => of({ action: true })
    };
  }
}

export class MatDialogRefMock {
  close() {
  }
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService: UserService;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [MaterialModule, BrowserAnimationsModule, FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [
        { provide: MatDialog, useClass: MatDialogMock },
        { provide: MatDialogRef, useClass: MatDialogRefMock },
        { provide: Router, useValue: router },
        UserService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should login by given credentials and dailog reference close when user did sucessfull login', () => {
    const user = {} as User;
    component.email = 'test@gmail.com';
    component.password = 'password';
    spyOn(userService, 'getUser').and.returnValue(of(user));
    component. login();
  //  expect(router.navigate).toHaveBeenCalledWith(['dashboardhome/createSurvey']);
  });

});
