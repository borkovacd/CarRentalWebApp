import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {Page} from '../model/page';
import {SearchUser} from '../model/search-user';

const API_URL = 'api/test/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', {responseType: 'text'});
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', {responseType: 'text'});
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', {responseType: 'text'});
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>('api/users');
  }

  getUsersPage(filters: SearchUser = null, page: number | null = null): Observable<Page<User>> {
    var options = {params: {}};
    if (page !== null) {
      options['params']['page'] = '' + (page - 1);
    }
    if (filters !== null) {
      if (filters.firstName != null) {
        options['params']['firstName'] = filters.firstName;
      }
      if (filters.lastName != null) {
        options['params']['lastName'] = filters.lastName;
      }
      if (filters.username != null) {
        options['params']['username'] = filters.username;
      }
      if (filters.isBlocked != null) {
        options['params']['isBlocked'] = filters.isBlocked;
      }
    }
    return this.http.get<Page<User>>('api/users', options);
  }
}
