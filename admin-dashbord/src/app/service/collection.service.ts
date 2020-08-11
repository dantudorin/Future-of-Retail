import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, from } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Collection } from '../model/collection';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  serverURL = 'http://localhost:8062';
  createCollectionAPI = "/store";
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  createCollection(collection : Collection) : Observable<Collection> {
    return this.http.post<Collection>(this.serverURL + this.createCollectionAPI, collection, this.httpOptions)
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
      'Something bad happened; please try again later'
    );
  }
}
