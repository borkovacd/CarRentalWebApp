import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {VehicleBrand} from '../model/vehicle-brand';

@Injectable({
  providedIn: 'root'
})
export class VehicleBrandService {

  constructor(private client: HttpClient) {
  }

  getVehicleBrands() : Observable<VehicleBrand[]> {
    return this.client.get<VehicleBrand[]>("api/brands");
  }
}
