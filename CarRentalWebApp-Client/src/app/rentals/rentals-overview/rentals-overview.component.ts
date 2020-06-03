import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from '../../model/user';
import {Rental} from '../../model/rental';

@Component({
  selector: 'app-rentals-overview',
  templateUrl: './rentals-overview.component.html',
  styleUrls: ['./rentals-overview.component.css']
})
export class RentalsOverviewComponent implements OnInit {

  @Input("items")
  items: Rental[] = [];

  @Output('onRentalDelete')
  private onRentalDelete: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit(): void {
  }

  deleteRental(id: number) {
    this.onRentalDelete.next(id);
  }

}
