import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-browse-page',
  templateUrl: './browse-page.component.html',
  styleUrls: ['./browse-page.component.scss']
})
export class BrowsePageComponent implements OnInit {

  mockImgs:string[] = [
    'img1',
    'img2',
    'img3',
    'img4',
    'img5',
    'img6',
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
