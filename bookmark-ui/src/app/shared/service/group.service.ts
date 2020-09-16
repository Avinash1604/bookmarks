import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Group } from '../model/group';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  baseUrl = 'https://bookmarks-tiny.herokuapp.com/api/v1/bookmarks/groups';

  constructor(private http: HttpClient) {}

  public createGroup(data: Group): Observable<Group> {
    return this.http.post<Group>(this.baseUrl, data);
  }
  public getGroups(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }
  public getGroupsById(groupId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + '?groupId=' + groupId);
  }
  public updateGroup(data: Group): Observable<any> {
    return this.http.put(this.baseUrl, data);
  }
  public deleteGroup(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + '/' + id);
  }
  public deleteUrl(groupId: number, id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + '/' + groupId + '/urls/' + id);
  }
  public updateUrl(data: Group): Observable<any> {
    return this.http.put(this.baseUrl + '/urls', data);
  }
  public addUrls(data: Group): Observable<any> {
    return this.http.post(this.baseUrl + '/urls', data);
  }

  public addusers(data: Group): Observable<any> {
    return this.http.post(this.baseUrl + '/users', data);
  }

  public updateUsers(data: Group): Observable<any> {
    return this.http.put(this.baseUrl + '/users/roles', data);
  }
  public deleteUsers(groupId: number, id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + '/' + groupId + '/users/' + id);
  }
}
