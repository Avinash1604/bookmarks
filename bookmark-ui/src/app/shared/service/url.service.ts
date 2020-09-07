import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Url } from '../model/url';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  baseUrl = 'https://bookmarks-tiny.herokuapp.com/api/v1/urls/shorts';

  constructor(private http: HttpClient) {}

  requestShortUrl(data: Url): Observable<Url> {
    return this.http.post<Url>(this.baseUrl, data);
  }
}
