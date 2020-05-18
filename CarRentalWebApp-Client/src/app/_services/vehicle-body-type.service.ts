import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {VehicleBrand} from '../model/vehicle-brand';
import {VehicleBodyType} from '../model/vehicle-body-type';

@Injectable({
  providedIn: 'root'
})
export class VehicleBodyTypeService {

  constructor(private client: HttpClient) {
  }

  getVehicleBodyTypes(): Observable<VehicleBodyType[]> {
    return this.client.get<VehicleBodyType[]>("api/bodyTypes");
  }
}
