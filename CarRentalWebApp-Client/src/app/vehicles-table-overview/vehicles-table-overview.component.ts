import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Vehicle} from '../model/vehicle';
import {VehicleService} from '../service/vehicle.service';
import {Search} from '../model/search';

@Component({
  selector: 'app-vehicles-table-overview',
  templateUrl: './vehicles-table-overview.component.html',
  styleUrls: ['./vehicles-table-overview.component.css']
})
export class VehiclesTableOverviewComponent implements OnInit {

  @Input('items')
  items: Vehicle[] = [];

  @Output('onVehicleDelete')
  private onVehicleDelete: EventEmitter<number> = new EventEmitter<number>();

  constructor(private vehicleService: VehicleService) {
  }

  ngOnInit(): void {
  }

  deleteVehicle(id: number) {
    this.onVehicleDelete.next(id);
  }
}
