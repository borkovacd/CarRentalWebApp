import {Component, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VehicleService} from '../service/vehicle.service';
import {Vehicle} from '../model/vehicle';
import {Page} from '../model/page';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {

  vehicleId : number = null;

  @Output("item")
  vehicle : Vehicle = null;


  constructor(private route: ActivatedRoute,
              private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.vehicleId = +params['id'];
      this.vehicleService.getVehicle(this.vehicleId).subscribe( data => {
        this.vehicle = data;
      })
    })
  }

}
