import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalPriceFormComponent } from './rental-price-form.component';

describe('RentalPriceFormComponent', () => {
  let component: RentalPriceFormComponent;
  let fixture: ComponentFixture<RentalPriceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalPriceFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalPriceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
