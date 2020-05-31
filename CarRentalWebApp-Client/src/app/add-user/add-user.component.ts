import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../_services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  @ViewChild('addUserForm') addUserForm: NgForm;

  userId: number;
  updating : boolean = false;
  addOrUpdateUser: string = '';

  user: User = {
    id: null,
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    phoneNumber: '',
    address: '',
    dateOfBirth: '',
    blocked: false,
    deleted: false,
    userType: 'USER',
    password: ''
  };

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
      if (isNaN(this.userId)) {
        this.addOrUpdateUser = 'Dodaj korisnika';
        this.updating = false;
      } else {
        this.addOrUpdateUser = 'Ažuriraj korisnika';
        this.updating = true;
      }
    });

    if (!isNaN(this.userId)) {
      //Updating user
      this.userService.getUser(this.userId).subscribe(data => {
        console.log(data);
        this.user.id = data.id;
        this.user.userType = data.userType;
        this.user.firstName = data.firstName;
        this.user.lastName = data.lastName;
        this.user.username = data.username;
        this.user.email = data.email;
        this.user.phoneNumber = data.phoneNumber;
        this.user.address = data.address;
        this.user.dateOfBirth = data.dateOfBirth;
        this.user.password = data.password;
        this.user.deleted = data.deleted;
        this.user.blocked = data.blocked;
      });
    }
  }

  onClear() {
    this.addUserForm.reset();
  }

  addUser() {
    this.userService.saveUser(this.user).subscribe(user => {
      console.log(user);
      this.router.navigateByUrl('users');
    });
  }
}
