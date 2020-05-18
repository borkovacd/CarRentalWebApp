import {Component, OnInit, Output} from '@angular/core';
import {Search} from '../model/search';
import {SearchUser} from '../model/search-user';
import {Page} from '../model/page';
import {Vehicle} from '../model/vehicle';
import {User} from '../model/user';
import {UserService} from '../_services/user.service';

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent implements OnInit {

  filterFirstName: string = "";
  filterLastName: string = "";
  filterUsername: string = "";
  filterBlocked: boolean = null;
  allFilters : SearchUser = null;
  private page: number = 1;

  @Output("items")
  usersPage: Page<User> = {currentPage: 0, itemsPerPage: 0, totalItems: 0, items: []}

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.refreshView();
  }

  public refreshView(page: number = 1) {
    this.userService.getUsersPage(this.allFilters,this.page).subscribe(usersPage => {
      usersPage.currentPage += 1;
      this.usersPage = usersPage;
    })
  }

  public setPage(page: number) {
    this.page = page;
    this.refreshView();
  }

  public setFilter(filters: SearchUser) {
    this.allFilters = filters;
    this.refreshView();
  }

}
