import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
   baseUrl = 'https://bookmarks-tiny.herokuapp.com/api/v1/users';
  //baseUrl = 'http://localhost:8080/api/v1/users';
  getUserByCredentials = '/by-credentials';

  constructor(private http: HttpClient) {}

  creatUser(data: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, data);
  }

  getUser(data: User): Observable<User> {
    return this.http.get<User>(
      this.baseUrl +
        this.getUserByCredentials +
        '?email=' +
        data.email +
        '&password=' +
        data.password
    );
  }

  getAllUsers(): Observable<any> {
    return this.http.get(this.baseUrl);
  }
}
