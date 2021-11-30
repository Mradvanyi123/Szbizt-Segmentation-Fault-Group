import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullImageDisplayComponent } from './full-image-display.component';

describe('FullImageDisplayComponent', () => {
  let component: FullImageDisplayComponent;
  let fixture: ComponentFixture<FullImageDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullImageDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FullImageDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
