import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../../model/user';
import {Rental} from '../../model/rental';
import {UserService} from '../../_services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {VehicleService} from '../../service/vehicle.service';
import {RentalService} from '../../_services/rental.service';
import {Vehicle} from '../../model/vehicle';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-add-rental',
  templateUrl: './add-rental.component.html',
  styleUrls: ['./add-rental.component.css']
})
export class AddRentalComponent implements OnInit {

  @ViewChild('addRentalForm') addRentalForm: NgForm;
  @ViewChild('startDateField') startDateField : any;
  @ViewChild('endDateField') endDateField : any;

  rentalId: number;
  updating : boolean = false;
  addOrUpdateRental: string = '';

  vehicles: Vehicle[] = [];
  users: User[] = [];

  startDate: any = null;
  endDate: any = null;
  amount : number = null;


  rental: Rental = {
    id: null,
    vehicle: null,
    user: null,
    startDate: null,
    endDate: null,
    deleted: false,
  };

  constructor(private rentalService: RentalService,
              private vehicleService: VehicleService,
              private userService: UserService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.rentalId = +params['id'];
      if (isNaN(this.rentalId)) {
        this.addOrUpdateRental = 'Dodaj iznajmljivanje';
        this.updating = false;
      } else {
        this.addOrUpdateRental = 'Ažuriraj iznajmljivanje';
        this.updating = true;
      }
    });

    this.vehicleService.getVehicles().subscribe(vehicles => {
      //console.log(vehicles);
      this.vehicles = vehicles;
    });
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });

    if (!isNaN(this.rentalId)) {
      //Updating rental
      this.rentalService.getRental(this.rentalId).subscribe(data => {
        console.log(data);
        this.rental.id = data.id;
        this.rental.user = data.user;
        this.rental.vehicle = data.vehicle;
        this.rental.startDate = data.startDate;
        this.rental.endDate = data.endDate;
        this.rental.deleted = data.deleted;
      });
    }
  }

  onClear() {
    this.addRentalForm.reset();
  }

  calculate() {
    if(this.rental.startDate != null && this.rental.endDate != null && this.rental.vehicle != null) {
      var date1 = new Date(this.rental.startDate);
      this.startDate = date1;
      var date2 = new Date(this.rental.endDate);
      var date2PlusDay = new Date(date2.getTime() + (1000 * 60 * 60 * 24));
      this.endDate = date2PlusDay;
      if(date2.valueOf() < date1.valueOf()) {
        alert("Both dates need to be valid! Try again!");
        this.rental.startDate = null;
        this.startDate = null;
        this.rental.endDate = null;
        this.endDate = null;
        this.startDateField.reset();
        this.endDateField.reset();
      } else {
        var days = (date2PlusDay.valueOf() - date1.valueOf())/1000/60/60/24;
        //alert((date2.valueOf() - date1.valueOf())/1000/60/60/24);
        this.amount = this.rental.vehicle.rentalPrice * days;
      }

    }
  }

  addRental() {
    this.rentalService.saveRental(this.rental).subscribe(rental => {
      console.log(rental);
      this.router.navigateByUrl('rentals');
    });
  }

}
