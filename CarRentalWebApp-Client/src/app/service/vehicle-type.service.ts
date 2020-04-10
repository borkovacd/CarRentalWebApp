import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {VehicleType} from '../model/vehicle-type';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehicleTypeService {

  constructor(private client: HttpClient) {
  }

  getVehicleTypes() : Observable<VehicleType[]> {
    return this.client.get<VehicleType[]>("api/types");
  }
}
