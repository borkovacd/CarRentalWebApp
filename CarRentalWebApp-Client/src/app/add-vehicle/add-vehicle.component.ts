import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {VehicleBrandService} from '../service/vehicle-brand.service';
import {VehicleTypeService} from '../service/vehicle-type.service';
import {VehicleFuelType} from '../model/vehicle-fuel-type';
import {VehicleBodyTypeService} from '../_services/vehicle-body-type.service';
import {VehicleFuelTypeService} from '../_services/vehicle-fuel-type.service';
import {FormArray, FormBuilder, FormControl, NgForm} from '@angular/forms';
import {VehicleService} from '../service/vehicle.service';
import {Vehicle} from '../model/vehicle';
import {ActivatedRoute, Router} from '@angular/router';
import {ImageService} from '../_services/image.service';

@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.css']
})
export class AddVehicleComponent implements OnInit {

  @ViewChild('addVehicleForm') addVehicleForm: NgForm;
  @ViewChild('fuelForm') fuelForm: any;
  @ViewChild('fileChooser') fileChooser: ElementRef;


  vehicleType: string = null;
  vehicleBrand: string = null;
  vehicleBodyType: string = null;
  vehicleEnginePower: number = null;
  vehicleEngineVolume: number = null;
  vehicleDoorsNumber: number = null;
  vehicleSeatsNumber: number = null;
  vehicleModel: string = '';
  vehicleDriveType: string = '';
  vehicleTransmissionType: string = '';
  vehicleDescription: string = '';
  vehicleGearsNumber: number = null;
  vehiclePrice: number = null;

  vehicleTypes: string[] = [];
  vehicleBodyTypes: string[] = [];
  vehicleBrands: string[] = [];
  vehicleFuelTypes: string[] = [];

  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;

  message: string;

  form: any;
  show: boolean = false;
  imageName: string = '';
  uploadPossible: boolean = false;
  imageUploaded: boolean = false;
  allRequiredDataEntered: boolean = false;

  vehicleId: number;
  addOrUpdateVehicle: string = '';

  vehicle: Vehicle = {
    id: null, typeName: '', brandName: '', model: '', bodyTypeName: '', fuelTypes: [],
    engineVolume: -1, enginePower: -1, transmissionType: '', gearNumber: -1, driveType: '',
    numberOfSeats: -1, numberOfDoors: -1, rentalPrice: -1, name: '', description: '', imageName: ''
  };

  constructor(private vehicleService: VehicleService,
              private vehicleBrandService: VehicleBrandService,
              private vehicleTypeService: VehicleTypeService,
              private vehicleBodyTypeService: VehicleBodyTypeService,
              private vehicleFuelTypeService: VehicleFuelTypeService,
              private imageService: ImageService,
              private formBuilder: FormBuilder,
              private httpClient: HttpClient,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.vehicleId = +params['id'];
      //console.log(this.vehicleId);
      if (isNaN(this.vehicleId)) {
        this.addOrUpdateVehicle = 'Dodaj vozilo';
      } else {
        this.addOrUpdateVehicle = 'Ažuriraj vozilo';
      }
    });

    this.show = false;
    this.uploadPossible = false;
    this.imageUploaded = false;
    this.allRequiredDataEntered = false;

    this.vehicleBrandService.getVehicleBrands().subscribe(vehicleBrands => {
      for (let i of vehicleBrands) {
        this.vehicleBrands.push(i.name);
      }
    });
    this.vehicleTypeService.getVehicleTypes().subscribe(vehicleTypes => {
      for (let i of vehicleTypes) {
        this.vehicleTypes.push(i.name);
      }
    });
    this.vehicleBodyTypeService.getVehicleBodyTypes().subscribe(vehicleBodyTypes => {
      for (let i of vehicleBodyTypes) {
        this.vehicleBodyTypes.push(i.name);
      }
    });
    this.vehicleFuelTypeService.getVehicleFuelTypes().subscribe(vehicleFuelTypes => {
      for (let i of vehicleFuelTypes) {
        this.vehicleFuelTypes.push(i.name);
      }
      this.form = this.formBuilder.group({
        fuelTypes: new FormArray([])
      });

      if (!isNaN(this.vehicleId)) {
        //Updating a vehicle
        this.vehicleService.getVehicle(this.vehicleId).subscribe(data => {
          this.vehicle = data;
          this.vehicleBrand = this.vehicle.brandName;
          this.vehicleEnginePower = this.vehicle.enginePower;
          this.vehicleDoorsNumber = this.vehicle.numberOfDoors;
          this.vehicleModel = this.vehicle.model;
          this.vehicleEngineVolume = this.vehicle.engineVolume;
          this.vehicleSeatsNumber = this.vehicle.numberOfSeats;
          this.vehicleDriveType = this.vehicle.driveType;
          this.vehicleTransmissionType = this.vehicle.transmissionType;
          this.vehicleGearsNumber = this.vehicle.gearNumber;
          this.vehicleDescription = this.vehicle.description;
          this.vehicleType = this.vehicle.typeName;
          this.vehicleBodyType = this.vehicle.bodyTypeName;
          this.vehiclePrice = this.vehicle.rentalPrice;
          this.updateCheckboxes(this.vehicle.fuelTypes);
          this.show = true;
          this.imageName = this.vehicle.imageName;
          this.allRequiredDataEntered = true;

        });
      } else {
        this.addCheckboxes();
        this.show = true;
      }

    });


  }

  private addCheckboxes() {
    this.vehicleFuelTypes.forEach((o, i) => {
      const control = new FormControl(i === 0); // if first item set to true, else false
      (this.form.controls.fuelTypes as FormArray).push(control);
    });
  }

  private updateCheckboxes(fuelTypes: string[]) {
    this.vehicleFuelTypes.forEach((o, i) => {
        const control = new FormControl(false);
        for (let i of fuelTypes) {
          if (o === i) {
            control.setValue(true);
          }
        }
        (this.form.controls.fuelTypes as FormArray).push(control);
      }
    );
  }

  //Gets called when the user selects an image
  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.uploadPossible = true;
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.imageService.uploadImage(uploadImageData).subscribe(res => {
        //console.log(res.message);
        this.imageName = res.message;
        this.imageUploaded = true;
        this.allRequiredDataEntered = true;
      }
    );

    /*//Make a call to the Spring Boot Application to save the image
    this.httpClient.post('api/image/upload', uploadImageData)
      .subscribe( res => {
        console.log(res);
        this.imageName = res.toString();
        }
      );*/
  }

  //Gets called when the user clicks on retieve image button to get the image from back end

  getImage() {
    //Make a call to Sprinf Boot to get the Image Bytes.
    this.httpClient.get('api/image/get/' + this.imageName)
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }

  addVehicle() {

    const selectedFuelTypesNames = this.form.value.fuelTypes
      .map((v, i) => (v ? this.vehicleFuelTypes[i] : null))
      .filter(v => v !== null);
    console.log(selectedFuelTypesNames);

    if (isNaN(this.vehicleId)) { //Adding new vehicle
      this.vehicle = {
        id: null,
        bodyTypeName: this.vehicleBodyType,
        brandName: this.vehicleBrand,
        description: this.vehicleDescription,
        driveType: this.vehicleDriveType,
        enginePower: this.vehicleEnginePower,
        engineVolume: this.vehicleEngineVolume,
        fuelTypes: selectedFuelTypesNames,
        gearNumber: this.vehicleGearsNumber,
        imageName: this.imageName,
        model: this.vehicleModel,
        name: this.vehicleBrand + ' ' + this.vehicleModel,
        numberOfDoors: this.vehicleDoorsNumber,
        numberOfSeats: this.vehicleSeatsNumber,
        rentalPrice: this.vehiclePrice,
        transmissionType: this.vehicleTransmissionType,
        typeName: this.vehicleType
      };
    } else { //Updating existing vehicle
      this.vehicle = {
        id: this.vehicleId,
        bodyTypeName: this.vehicleBodyType,
        brandName: this.vehicleBrand,
        description: this.vehicleDescription,
        driveType: this.vehicleDriveType,
        enginePower: this.vehicleEnginePower,
        engineVolume: this.vehicleEngineVolume,
        fuelTypes: selectedFuelTypesNames,
        gearNumber: this.vehicleGearsNumber,
        imageName: this.imageName,
        model: this.vehicleModel,
        name: this.vehicleBrand + ' ' + this.vehicleModel,
        numberOfDoors: this.vehicleDoorsNumber,
        numberOfSeats: this.vehicleSeatsNumber,
        rentalPrice: this.vehiclePrice,
        transmissionType: this.vehicleTransmissionType,
        typeName: this.vehicleType
      };
    }
    this.vehicleService.saveVehicle(this.vehicle).subscribe(vehicle => {
      console.log(vehicle);
      this.router.navigateByUrl('vehicles-admin');
    });
  }

  onClear() {
    // Now that we have access to the form via the 'ViewChild', we can access the form and clear it.
    this.fileChooser.nativeElement.value = '';
    this.selectedFile = null;
    this.imageUploaded = false;
    this.uploadPossible = false;
    this.imageName = '';
    this.addVehicleForm.reset();

  }


}


