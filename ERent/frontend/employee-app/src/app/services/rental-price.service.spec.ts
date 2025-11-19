import { TestBed } from '@angular/core/testing';

import { RentalPriceService } from './rental-price.service';

describe('RentalPriceService', () => {
  let service: RentalPriceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentalPriceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
