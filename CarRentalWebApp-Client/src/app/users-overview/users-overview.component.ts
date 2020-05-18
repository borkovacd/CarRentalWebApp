import {Component, Input, OnInit} from '@angular/core';
import {Vehicle} from '../model/vehicle';
import {User} from '../model/user';

@Component({
  selector: 'app-users-overview',
  templateUrl: './users-overview.component.html',
  styleUrls: ['./users-overview.component.css']
})
export class UsersOverviewComponent implements OnInit {

  @Input("items")
  items: User[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  blockUser(id: number) {

  }

  deleteUser(id: number) {

  }
}
