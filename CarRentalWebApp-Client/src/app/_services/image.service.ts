import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {VehicleBodyType} from '../model/vehicle-body-type';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private client: HttpClient) {

  }

  uploadImage(uploadImageData: FormData): Observable<any> {
    return this.client.post('api/image/upload', uploadImageData);
  }
}
