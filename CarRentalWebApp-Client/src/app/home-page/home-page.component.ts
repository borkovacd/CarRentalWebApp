import {Component, OnInit, Output} from '@angular/core';
import {Page} from '../model/page';
import {Vehicle} from '../model/vehicle';
import {VehicleService} from '../service/vehicle.service';
import {Search} from '../model/search';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  filterVehicleType: string = "";
  filterVehicleBrand: string = "";
  filterVehicleModel: string = "";
  filterVehicleLowestPrice: number = null;
  filterVehicleHighestPrice: number = null;
  allFilters : Search = null;
  private page: number = 1;



  @Output("items")
  vehiclesPage: Page<Vehicle> = {currentPage: 0, itemsPerPage: 0, totalItems: 0, items: []}

  constructor(private vehicleService: VehicleService) {
  }


  ngOnInit(): void {
    this.refreshView();
  }

  public refreshView(page: number = 1) {
    this.vehicleService.getVehiclesPage(this.allFilters,this.page).subscribe(vehiclesPage => {
      vehiclesPage.currentPage += 1;
      this.vehiclesPage = vehiclesPage;
    })
  }

  public setPage(page: number) {
    this.page = page;
    this.refreshView();
  }

  public setFilter(filters: Search) {
    this.allFilters = filters;
    this.filterVehicleType = filters.type;
    this.filterVehicleBrand = filters.brand;
    this.filterVehicleModel = filters.model;
    this.filterVehicleLowestPrice = filters.lowestPrice;
    this.filterVehicleHighestPrice = filters.highestPrice;
    this.refreshView();
  }


}
