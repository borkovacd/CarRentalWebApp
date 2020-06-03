import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {filter} from 'rxjs/operators';
import {Page} from '../model/page';
import {Vehicle} from '../model/vehicle';
import {Search} from '../model/search';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  constructor(private client: HttpClient) { }

  public getVehiclesPage(filters: Search = null, page: number | null = null): Observable<Page<Vehicle>> {
    var options = {params: {}};
    if(page !== null) {
      options["params"]["page"] = ""+(page-1);
    }
    if(filters !== null) {
      if(filters.type != null) {
        options["params"]["type"] = filters.type;
      }
      if(filters.brand != null) {
        options["params"]["brand"] = filters.brand;
      }
      if(filters.model != null) {
        options["params"]["model"] = filters.model;
      }
      if(filters.lowestPrice != null) {
        options["params"]["lowestPrice"] = filters.lowestPrice;
      }
      if(filters.highestPrice != null) {
        options["params"]["highestPrice"] = filters.highestPrice;
      }
    }
    return this.client.get<Page<Vehicle>>("api/vehicles", options)
  }

  getVehicles() : Observable<Vehicle[]>{
    return this.client.get<Vehicle[]>("api/vehicles");
  }

  getVehicle(id: number) : Observable<Vehicle>{
    return this.client.get<Vehicle>("api/vehicles/"+id);
  }

  public saveVehicle(item: Vehicle) : Observable<Vehicle> {
    var url = "api/vehicles"
    if(item.id) {
      url += `/${item.id}`;
      return this.client.put<Vehicle>(url, item);
    } else {
      return this.client.post<Vehicle>(url, item);
    }
  }

  deleteVehicle(id: number) {
    return this.client.delete(`api/vehicles/${id}`);
  }
}
