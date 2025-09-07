import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MalfunctionFormComponent } from './malfunction-form.component';

describe('MalfunctionFormComponent', () => {
  let component: MalfunctionFormComponent;
  let fixture: ComponentFixture<MalfunctionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MalfunctionFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MalfunctionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
