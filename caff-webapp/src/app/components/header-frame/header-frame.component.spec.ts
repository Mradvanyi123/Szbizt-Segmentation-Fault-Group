import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { RouterTestingModule } from '@angular/router/testing';

import { HeaderFrameComponent } from './header-frame.component';

describe('HeaderFrameComponent', () => {
  let component: HeaderFrameComponent;
  let fixture: ComponentFixture<HeaderFrameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[RouterTestingModule, MatDialogModule,MatMenuModule, HttpClientModule],
      declarations: [ HeaderFrameComponent ],
      providers: [
        { provide: MatDialogRef, useValue: {} }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderFrameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
