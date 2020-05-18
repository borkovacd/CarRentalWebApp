import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiclesTableOverviewComponent } from './vehicles-table-overview.component';

describe('VehiclesTableOverviewComponent', () => {
  let component: VehiclesTableOverviewComponent;
  let fixture: ComponentFixture<VehiclesTableOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehiclesTableOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehiclesTableOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
