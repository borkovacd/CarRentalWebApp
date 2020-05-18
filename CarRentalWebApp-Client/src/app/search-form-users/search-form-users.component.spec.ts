import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFormUsersComponent } from './search-form-users.component';

describe('SearchFormUsersComponent', () => {
  let component: SearchFormUsersComponent;
  let fixture: ComponentFixture<SearchFormUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFormUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFormUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
