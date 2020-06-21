import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomePageComponent} from './home-page/home-page.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {ProfileComponent} from './profile/profile.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {VehicleDetailsComponent} from './vehicle-details/vehicle-details.component';
import {RentalFormComponent} from './rental-form/rental-form.component';
import {AddVehicleComponent} from './add-vehicle/add-vehicle.component';
import {UsersPageComponent} from './users-page/users-page.component';
import {VehiclesPageComponent} from './vehicles-page/vehicles-page.component';
import {AddUserComponent} from './add-user/add-user.component';
import {RentalsPageComponent} from './rentals/rentals-page/rentals-page.component';
import {AddRentalComponent} from './rentals/add-rental/add-rental.component';
import {IndexComponent} from './index/index.component';

const routes: Routes = [
  { path: 'home', component: IndexComponent },
  { path: 'users', component: UsersPageComponent},
  { path: 'rentals', component: RentalsPageComponent },
  { path: 'vehicles-admin', component: VehiclesPageComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'add-vehicle', component: AddVehicleComponent },
  { path: 'add-vehicle/:id', component: AddVehicleComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'add-user/:id', component: AddUserComponent },
  { path: 'add-rental', component: AddRentalComponent },
  { path: 'add-rental/:id', component: AddRentalComponent },
  { path: 'details/:id', component: VehicleDetailsComponent },
  { path: 'renting/:id', component: RentalFormComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
