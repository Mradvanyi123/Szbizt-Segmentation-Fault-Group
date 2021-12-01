import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FullImageDialogData } from '../post/post.component';

@Component({
  selector: 'app-full-image-display',
  templateUrl: './full-image-display.component.html',
  styleUrls: ['./full-image-display.component.scss']
})
export class FullImageDisplayComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: FullImageDialogData,
  ) { }

  ngOnInit(): void {
  }

}
