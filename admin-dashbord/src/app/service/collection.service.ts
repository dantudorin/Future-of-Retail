import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, from } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Collection } from '../model/collection';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  serverURL = 'http://localhost:8082';
  collectionAPI = "/store";
  PageAPI = "/page/";
  filterApi = "/filter-stores";
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  createCollection(collection: Collection): Observable<Collection> {
    return this.http.post<Collection>(this.serverURL + this.collectionAPI, collection, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      )
  }

  getCollectionFromPage(pageNumber: Number = 1, pageSize: Number = 1, sortBy: String = "id"): Observable<Collection[]> {
    return this.http.get<Collection[]>(this.serverURL + this.collectionAPI + this.PageAPI + pageNumber.toString())
      .pipe(
        catchError(this.handleError)
      )
  }

  getStoresBySearchedName(storeName: string): Observable<Collection[]> {
    return this.http.get<Collection[]>(this.serverURL + this.collectionAPI + this.filterApi +'?storeName='+storeName)
      .pipe(
        catchError(this.handleError)
      )
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('Client Side Error: ', error.error.message);
    } else {
      console.error('Server Side Error: ', error);
    }

    return throwError(
      'Something bad happened; Please try again later.'
    );
  }
}
