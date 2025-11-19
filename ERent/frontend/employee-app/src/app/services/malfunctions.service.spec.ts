import { TestBed } from '@angular/core/testing';

import { MalfunctionsService } from './malfunctions.service';

describe('MalfunctionsService', () => {
  let service: MalfunctionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MalfunctionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
