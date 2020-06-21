import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HomePageComponent} from './home-page/home-page.component';
import {VehiclesOverviewComponent} from './vehicles-overview/vehicles-overview.component';
import {SearchFormComponent} from './search-form/search-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {authInterceptorProviders} from './_helpers/auth.interceptor';
import {VehicleDetailsComponent} from './vehicle-details/vehicle-details.component';
import {CarouselModule} from 'ngx-bootstrap/carousel';
import {DetailsComponent} from './details/details.component';
import {RentalFormComponent} from './rental-form/rental-form.component';
import {MomentModule} from 'angular2-moment';
import {AddVehicleComponent} from './add-vehicle/add-vehicle.component';
import {UsersPageComponent} from './users-page/users-page.component';
import {SearchFormUsersComponent} from './search-form-users/search-form-users.component';
import {UsersOverviewComponent} from './users-overview/users-overview.component';
import {VehiclesTableOverviewComponent} from './vehicles-table-overview/vehicles-table-overview.component';
import {VehiclesPageComponent} from './vehicles-page/vehicles-page.component';
import {AddUserComponent} from './add-user/add-user.component';
import {ForbiddenValidatorDirective} from './_custom_validators/forbidden-name.directive';
import {YesNoPipe} from './pipes/yes-no.pipe';
import {RentalsPageComponent} from './rentals/rentals-page/rentals-page.component';
import {RentalsOverviewComponent} from './rentals/rentals-overview/rentals-overview.component';
import {SearchFormRentalsComponent} from './rentals/search-form-rentals/search-form-rentals.component';
import {AddRentalComponent} from './rentals/add-rental/add-rental.component';
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {TabsModule} from 'ngx-bootstrap/tabs';
import {MyNavbarComponent} from './my-navbar/my-navbar.component';
import {CommonModule} from '@angular/common';
import {ProgressbarModule} from 'ngx-bootstrap/progressbar';
import {PopoverModule} from 'ngx-bootstrap/popover';
import {JwBootstrapSwitchNg2Module} from 'jw-bootstrap-switch-ng2';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {AlertModule} from 'ngx-bootstrap/alert';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {ModalModule} from 'ngx-bootstrap/modal';
import {IndexComponent} from './index/index.component';

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
    AddRentalComponent,
    MyNavbarComponent,
    IndexComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    BrowserAnimationsModule,
    MomentModule,
    ReactiveFormsModule,
    CommonModule,
    BrowserModule,
    FormsModule,
    RouterModule,
    BsDropdownModule.forRoot(),
    ProgressbarModule.forRoot(),
    TooltipModule.forRoot(),
    PopoverModule.forRoot(),
    CollapseModule.forRoot(),
    JwBootstrapSwitchNg2Module,
    TabsModule.forRoot(),
    PaginationModule.forRoot(),
    AlertModule.forRoot(),
    BsDatepickerModule.forRoot(),
    CarouselModule.forRoot(),
    ModalModule.forRoot(),
  ],
  providers: [authInterceptorProviders],
  exports: []
  ,
  bootstrap: [AppComponent]
})
export class AppModule {
}
