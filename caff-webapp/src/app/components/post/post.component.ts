import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Post } from 'src/app/structures/Post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post:Post ={id:'',title:'', img:'', userName:'', comments:[]}

  constructor(private pictureService:PictureHandlerService, private authService:AuthService) { }

  ngOnInit(): void {
  }

  async onAddComment(target:HTMLInputElement):Promise<void>{
    await this.pictureService.addComment(this.post.id,{userName:this.authService.loggedInUser!.name, text:target.value});
    target.value='';
    target.blur();
  }

}
