import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SearchUser} from '../model/search-user';

@Component({
  selector: 'app-search-form-users',
  templateUrl: './search-form-users.component.html',
  styleUrls: ['./search-form-users.component.css']
})
export class SearchFormUsersComponent implements OnInit {

  filterFirstName: string = "";
  filterLastName: string = "";
  filterUsername: string = "";
  filterBlocked: string = "";

  @Output("onSearch")
  private onSearch: EventEmitter<SearchUser> = new EventEmitter<SearchUser>();

  constructor() { }

  ngOnInit(): void {
  }

  search() {
    let isBlocked: boolean = false;
    if(this.filterBlocked == "Da") {
      isBlocked = true;
    }
    this.onSearch.next({firstName: this.filterFirstName, lastName: this.filterLastName, username: this.filterUsername, isBlocked: isBlocked});
  }

}
