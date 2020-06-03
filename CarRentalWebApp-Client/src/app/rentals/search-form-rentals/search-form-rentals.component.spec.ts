import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFormRentalsComponent } from './search-form-rentals.component';

describe('SearchFormRentalsComponent', () => {
  let component: SearchFormRentalsComponent;
  let fixture: ComponentFixture<SearchFormRentalsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFormRentalsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFormRentalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
