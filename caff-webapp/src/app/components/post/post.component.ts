import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post:string =''//later {title, img, comments ...}

  constructor() { }

  ngOnInit(): void {
  }

}
