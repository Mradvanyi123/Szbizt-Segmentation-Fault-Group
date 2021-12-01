import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FullImageDialogData } from '../post/post.component';

import { FullImageDisplayComponent } from './full-image-display.component';

describe('FullImageDisplayComponent', () => {
  let component: FullImageDisplayComponent;
  let fixture: ComponentFixture<FullImageDisplayComponent>;

  const data:FullImageDialogData={
    title: 'test',
    imageData:''
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullImageDisplayComponent ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: data },
        { provide: MatDialogRef, useValue: {} }
    ]
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
