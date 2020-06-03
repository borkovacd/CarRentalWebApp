import {Component, OnInit, Output} from '@angular/core';
import {Page} from '../../model/page';
import {SearchRental} from '../../model/search-rental';
import {Rental} from '../../model/rental';
import {RentalService} from '../../_services/rental.service';

@Component({
  selector: 'app-rentals-page',
  templateUrl: './rentals-page.component.html',
  styleUrls: ['./rentals-page.component.css']
})
export class RentalsPageComponent implements OnInit {

  searchFilters : SearchRental = null;
  private page: number = 1;

  @Output("items")
  rentalsPage: Page<Rental> = {currentPage: 0, itemsPerPage: 0, totalItems: 0, items: []}

  constructor(private rentalService : RentalService) {

  }

  ngOnInit(): void {
    this.refreshView();
  }

  public refreshView(page: number = 1) {
    this.rentalService.getRentalsPage(this.searchFilters,this.page).subscribe(rentalsPage => {
      rentalsPage.currentPage += 1;
      this.rentalsPage = rentalsPage;
    })
  }

  public setPage(page: number) {
    this.page = page;
    this.refreshView();
  }

  public setFilter(filters: SearchRental) {
    this.searchFilters = filters;
    this.refreshView();
  }

  deleteRental(id: number) {
    this.rentalService.deleteRental(id).subscribe( () => {
      this.refreshView();
    });
  }

}
