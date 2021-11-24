import { Component, Input, OnInit } from '@angular/core';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Post } from 'src/app/structures/Post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post:Post ={id:'', img:'', userName:'', comments:[]}

  constructor(private pictureService:PictureHandlerService) { }

  ngOnInit(): void {
  }

  async onAddComment(target:HTMLInputElement):Promise<void>{
    await this.pictureService.addComment(this.post.id, target.value);
    target.value='';
    target.blur();
  }

}
