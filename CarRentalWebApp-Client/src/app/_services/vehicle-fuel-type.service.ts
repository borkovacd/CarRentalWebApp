import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {VehicleBodyType} from '../model/vehicle-body-type';
import {VehicleBrand} from '../model/vehicle-brand';
import {VehicleFuelType} from '../model/vehicle-fuel-type';

@Injectable({
  providedIn: 'root'
})
export class VehicleFuelTypeService {

  constructor(private client: HttpClient) {
  }

  getVehicleFuelTypes(): Observable<VehicleFuelType[]> {
    return this.client.get<VehicleFuelType[]>("api/fuelTypes");
  }
}
