import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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

  @Output('onUserBlock')
  private onUserBlock: EventEmitter<User> = new EventEmitter<User>();

  @Output('onUserDelete')
  private onUserDelete: EventEmitter<number> = new EventEmitter<number>();


  constructor() { }

  ngOnInit(): void {
  }

  blockUser(user: User) {
    this.onUserBlock.next(user);
  }

  deleteUser(id: number) {
    this.onUserDelete.next(id);
  }
}
