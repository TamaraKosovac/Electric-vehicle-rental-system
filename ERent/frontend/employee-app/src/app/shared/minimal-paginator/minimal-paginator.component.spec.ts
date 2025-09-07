import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MinimalPaginatorComponent } from './minimal-paginator.component';

describe('MinimalPaginatorComponent', () => {
  let component: MinimalPaginatorComponent;
  let fixture: ComponentFixture<MinimalPaginatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MinimalPaginatorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MinimalPaginatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
