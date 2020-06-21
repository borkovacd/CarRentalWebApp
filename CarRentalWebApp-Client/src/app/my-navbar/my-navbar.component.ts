import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';

@Component({
  selector: 'app-my-navbar',
  templateUrl: './my-navbar.component.html',
  styleUrls: ['./my-navbar.component.css']
})
export class MyNavbarComponent implements OnInit {

  isCollapsed = true;
  private roles: string[];
  isLoggedIn = false;
  showAdminPage = false;
  username: string;


  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminPage = this.roles.includes('ROLE_ADMIN');

      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
