import {Component, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VehicleService} from '../service/vehicle.service';
import {Vehicle} from '../model/vehicle';
import {chunkByNumber} from 'ngx-bootstrap/carousel/utils';

@Component({
  selector: 'app-rental-form',
  templateUrl: './rental-form.component.html',
  styleUrls: ['./rental-form.component.css']
})
export class RentalFormComponent implements OnInit {

  vehicleId : number = null;
  @Output("item")
  vehicle : Vehicle = null;

  startDate: any = null;
  endDate: any = null;

  amount : number = null;

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


  rent() {

  }

  calculate() {
    if(this.startDate != null && this.endDate != null) {
      var date1 = new Date(this.startDate)
      var date2 = new Date(this.endDate)
      if(date2.valueOf() < date1.valueOf()) {
        alert("Both dates need to be valid! Try again!");
        this.startDate = null;
        this.endDate = null
      } else {
        var days = ((date2.valueOf() - date1.valueOf())/1000/60/60/24)+1;
        //alert((date2.valueOf() - date1.valueOf())/1000/60/60/24);
        this.amount = this.vehicle.rentalPrice * days;
      }

    }
  }
}
