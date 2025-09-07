import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsMapComponent } from './rentals-map.component';

describe('RentalsMapComponent', () => {
  let component: RentalsMapComponent;
  let fixture: ComponentFixture<RentalsMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalsMapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalsMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
