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

const routes: Routes = [
  { path: 'home', component: HomePageComponent },
  { path: 'users', component: UsersPageComponent},
  { path: 'vehicles-admin', component: VehiclesPageComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'add-vehicle', component: AddVehicleComponent },
  { path: 'add-vehicle/:id', component: AddVehicleComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'details/:id', component: VehicleDetailsComponent },
  { path: 'renting/:id', component: RentalFormComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
