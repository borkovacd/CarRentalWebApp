import {Component, Input, OnInit} from '@angular/core';
import {Vehicle} from '../model/vehicle';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  @Input("item")
  item: Vehicle;

  constructor() { }

  ngOnInit(): void {
  }

}
