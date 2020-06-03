import { BrowserModule } from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import { AppComponent } from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { HomePageComponent } from './home-page/home-page.component';
import { VehiclesOverviewComponent } from './vehicles-overview/vehicles-overview.component';
import {HttpClientModule} from '@angular/common/http';
import { SearchFormComponent } from './search-form/search-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { VehicleDetailsComponent } from './vehicle-details/vehicle-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CarouselModule} from 'ngx-bootstrap/carousel';
import { DetailsComponent } from './details/details.component';
import { RentalFormComponent } from './rental-form/rental-form.component';
import {MomentModule} from 'angular2-moment';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { UsersPageComponent } from './users-page/users-page.component';
import { SearchFormUsersComponent } from './search-form-users/search-form-users.component';
import { UsersOverviewComponent } from './users-overview/users-overview.component';
import { VehiclesTableOverviewComponent } from './vehicles-table-overview/vehicles-table-overview.component';
import { VehiclesPageComponent } from './vehicles-page/vehicles-page.component';
import { AddUserComponent } from './add-user/add-user.component';
import {ForbiddenValidatorDirective} from './_custom_validators/forbidden-name.directive';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { RentalsPageComponent } from './rentals/rentals-page/rentals-page.component';
import { RentalsOverviewComponent } from './rentals/rentals-overview/rentals-overview.component';
import { SearchFormRentalsComponent } from './rentals/search-form-rentals/search-form-rentals.component';
import { AddRentalComponent } from './rentals/add-rental/add-rental.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    VehiclesOverviewComponent,
    SearchFormComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardUserComponent,
    VehicleDetailsComponent,
    DetailsComponent,
    RentalFormComponent,
    AddVehicleComponent,
    UsersPageComponent,
    SearchFormUsersComponent,
    UsersOverviewComponent,
    VehiclesTableOverviewComponent,
    VehiclesPageComponent,
    AddUserComponent,
    ForbiddenValidatorDirective,
    YesNoPipe,
    RentalsPageComponent,
    RentalsOverviewComponent,
    SearchFormRentalsComponent,
    AddRentalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
    CarouselModule,
    MomentModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
