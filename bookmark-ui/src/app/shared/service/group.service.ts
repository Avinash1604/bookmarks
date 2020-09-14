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
  public updateGroup(data: Group): Observable<any> {
    return this.http.put(this.baseUrl, data);
  }
  public deleteGroup(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + '/' + id);
  }
}
