import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Usuario } from './usuario';
import { Item } from './item';
import { Carrinho } from './carrinho';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  baseurl = 'http://localhost:8191';

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  FecharCompras(data: Carrinho): Observable<Carrinho> {
    return this.http.post<Carrinho>(this.baseurl + '/carrinho/v1/', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  CreateUser(data: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.baseurl + '/users/v1/', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  CreateItem(data: Item): Observable<Item> {
    return this.http.post<Item>(this.baseurl + '/item/v1/', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  GetAllUsers(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.baseurl + '/users/v1/')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  GetAllItens(): Observable<Item[]> {
    return this.http.get<Item[]>(this.baseurl + '/item/v1/')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  GetCarrinhoByUserId(id: string): Observable<Carrinho[]> {
    return this.http.get<Carrinho[]>(this.baseurl + '/carrinho/v1/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  RemoveUser(id: string) {
    return this.http.delete<Usuario>(this.baseurl + '/users/v1/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  RemoveItem(id: number) {
    return this.http.delete<Item>(this.baseurl + '/item/v1/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  UpdateUser(data: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(this.baseurl + '/users/v1/', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  UpdateItem(data: Item): Observable<Item> {
    return this.http.put<Item>(this.baseurl + '/item/v1/', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  errorHandl(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
