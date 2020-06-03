import { Injectable } from '@angular/core';
import {SearchUser} from '../model/search-user';
import {Observable} from 'rxjs';
import {Page} from '../model/page';
import {User} from '../model/user';
import {HttpClient} from '@angular/common/http';
import {SearchRental} from '../model/search-rental';
import {Rental} from '../model/rental';
import {Vehicle} from '../model/vehicle';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  constructor(private http: HttpClient) { }

  getRentalsPage(filters: SearchRental = null, page: number | null = null): Observable<Page<Rental>> {
    var options = {params: {}};
    if (page !== null) {
      options['params']['page'] = "" + (page-1);
    }
    if (filters !== null) {
      if (filters.user != null) {
        options['params']['user'] = filters.user;
      }
      if (filters.vehicleType != null) {
        options['params']['vehicleType'] = filters.vehicleType;
      }
      if (filters.vehicleBrand != null) {
        options['params']['vehicleBrand'] = filters.vehicleBrand;
      }
      if (filters.vehicleModel != null) {
        options['params']['vehicleModel'] = filters.vehicleModel;
      }
      if (filters.startDate != null) {
        options['params']['startDate'] = filters.startDate;
      }
      if (filters.endDate != null) {
        options['params']['endDate'] = filters.endDate;
      }
    }
    return this.http.get<Page<Rental>>('api/rentals', options);
  }

  getRental(id: number): Observable<Rental> {
    return this.http.get<Rental>('api/rentals/' + id);
  }

  saveRental(item: Rental): Observable<Rental> {
    var url = "api/rentals";
    if(item.id) {
      url += `/${item.id}`;
      return this.http.put<Rental>(url, item);
    } else {
      return this.http.post<Rental>(url, item);
    }

  }

  deleteRental(id: number) {
    return this.http.delete(`api/rentals/${id}`);
  }
}
