import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {VehicleType} from '../../model/vehicle-type';
import {VehicleBrand} from '../../model/vehicle-brand';
import {Search} from '../../model/search';
import {SearchRental} from '../../model/search-rental';
import {VehicleBrandService} from '../../service/vehicle-brand.service';
import {VehicleTypeService} from '../../service/vehicle-type.service';

@Component({
  selector: 'app-search-form-rentals',
  templateUrl: './search-form-rentals.component.html',
  styleUrls: ['./search-form-rentals.component.css']
})
export class SearchFormRentalsComponent implements OnInit {

  filterUser: string = '';
  filterVehicleType: VehicleType = null;
  filterVehicleBrand: VehicleBrand = null;
  filterVehicleModel: string = '';
  filterStartDate: string = '';
  filterEndDate: string = '';

  vehicleTypes: VehicleType[] = [];
  vehicleBrands: VehicleBrand[] = [];

  @Output('onSearch')
  private onSearch: EventEmitter<SearchRental> = new EventEmitter<SearchRental>();


  constructor(private vehicleBrandService: VehicleBrandService,
              private vehicleTypeService: VehicleTypeService) {
  }

  ngOnInit(): void {
    this.vehicleBrandService.getVehicleBrands().subscribe(vehicleBrands => {
      this.vehicleBrands = vehicleBrands;
    });
    this.vehicleTypeService.getVehicleTypes().subscribe(vehicleTypes => {
      this.vehicleTypes = vehicleTypes;
    });
  }

  search() {
    if (this.filterVehicleType == null) {
      this.filterVehicleType = {id: -1, name: ''};
    }
    if (this.filterVehicleBrand == null) {
      this.filterVehicleBrand = {id: -1, name: ''};
    }
    this.onSearch.next(
      {
        user: this.filterUser,
        vehicleBrand: this.filterVehicleBrand.name,
        vehicleModel: this.filterVehicleModel,
        vehicleType: this.filterVehicleType.name,
        startDate: this.filterStartDate,
        endDate: this.filterEndDate
      });
  }

}
