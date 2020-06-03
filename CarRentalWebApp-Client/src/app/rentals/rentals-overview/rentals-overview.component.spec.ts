import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsOverviewComponent } from './rentals-overview.component';

describe('RentalsOverviewComponent', () => {
  let component: RentalsOverviewComponent;
  let fixture: ComponentFixture<RentalsOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalsOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalsOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
