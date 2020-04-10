import {Component, Input, OnInit} from '@angular/core';
import {Vehicle} from '../model/vehicle';

@Component({
  selector: 'app-vehicles-overview',
  templateUrl: './vehicles-overview.component.html',
  styleUrls: ['./vehicles-overview.component.css']
})
export class VehiclesOverviewComponent implements OnInit {

  @Input("items")
  items: Vehicle[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
