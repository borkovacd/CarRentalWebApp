import {Component, OnInit, Output} from '@angular/core';
import {Search} from '../model/search';
import {Page} from '../model/page';
import {Vehicle} from '../model/vehicle';
import {VehicleService} from '../service/vehicle.service';

@Component({
  selector: 'app-vehicles-page',
  templateUrl: './vehicles-page.component.html',
  styleUrls: ['./vehicles-page.component.css']
})
export class VehiclesPageComponent implements OnInit {

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
    this.refreshView();
  }

  deleteVehicle(id: number) {
    this.vehicleService.deleteVehicle(id).subscribe( () => {
      this.refreshView();
    });
  }
}
