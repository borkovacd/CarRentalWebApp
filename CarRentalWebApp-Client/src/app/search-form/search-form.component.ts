import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Search} from '../model/search';
import {VehicleType} from '../model/vehicle-type';
import {VehicleBrand} from '../model/vehicle-brand';
import {VehicleBrandService} from '../service/vehicle-brand.service';
import {VehicleTypeService} from '../service/vehicle-type.service';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  filterVehicleType: VehicleType = null;
  filterVehicleBrand: VehicleBrand = null;
  filterVehicleModel: string = "";
  filterVehicleLowestPrice: number = null;
  filterVehicleHighestPrice: number = null;

  vehicleTypes : VehicleType[] = [];
  vehicleBrands: VehicleBrand[] = [];

  @Output("onSearch")
  private onSearch: EventEmitter<Search> = new EventEmitter<Search>();


  constructor(private vehicleBrandService : VehicleBrandService,
              private vehicleTypeService: VehicleTypeService) { }

  ngOnInit(): void {
    this.vehicleBrandService.getVehicleBrands().subscribe(vehicleBrands => {
      this.vehicleBrands = vehicleBrands;
    });
    this.vehicleTypeService.getVehicleTypes().subscribe(vehicleTypes => {
      this.vehicleTypes = vehicleTypes;
    });

  }

  search() {
    if(this.filterVehicleType == null) {
      this.filterVehicleType = { id: -1, name : ''};
    }
    if(this.filterVehicleBrand == null) {
      this.filterVehicleBrand = { id: -1, name : ''};
    }
    this.onSearch.next({type: this.filterVehicleType.name, brand: this.filterVehicleBrand.name, model: this.filterVehicleModel, lowestPrice: this.filterVehicleLowestPrice, highestPrice: this.filterVehicleHighestPrice});
  }

}
