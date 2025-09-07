import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalPricesComponent } from './rental-prices.component';

describe('RentalPricesComponent', () => {
  let component: RentalPricesComponent;
  let fixture: ComponentFixture<RentalPricesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalPricesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalPricesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
