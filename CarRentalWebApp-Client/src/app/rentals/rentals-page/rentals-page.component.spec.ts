import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsPageComponent } from './rentals-page.component';

describe('RentalsPageComponent', () => {
  let component: RentalsPageComponent;
  let fixture: ComponentFixture<RentalsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
