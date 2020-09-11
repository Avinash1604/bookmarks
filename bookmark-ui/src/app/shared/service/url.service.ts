import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Url } from '../model/url';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  private addBookMarkLink = new Subject<boolean>();

  baseUrl = 'https://bookmarks-tiny.herokuapp.com/api/v1/urls/shorts';
  bookmarkUrl =
    'https://bookmarks-tiny.herokuapp.com/api/v1/bookmark/urls/shorts';

  constructor(private http: HttpClient) {}

  public requestShortUrl(data: Url): Observable<Url> {
    return this.http.post<Url>(this.baseUrl, data);
  }

 public getBookmarkedShortUrl(): Observable<any> {
    return this.http.get<any>(this.bookmarkUrl);
  }

 public deleteShortUrl(id: number): Observable<any> {
    return this.http.delete<any>(this.bookmarkUrl + '/' + id);
  }

  public updateShortUrl(url: Url): Observable<any> {
    return this.http.put(this.bookmarkUrl, url);
  }

  public isBookMarkLinkAdded(added: boolean){
    this.addBookMarkLink.next(added);
  }

  public get getBookmarkAdded(): Observable<boolean>{
    return this.addBookMarkLink.asObservable();
  }
}
