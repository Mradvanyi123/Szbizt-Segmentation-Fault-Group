import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Post } from 'src/app/structures/Post';
import { Roles } from 'src/app/structures/User';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post:Post ={id:'0',title:'', img:'', userName:'', comments:[]}
  titleIsEdit:boolean = false;

  constructor(private pictureService:PictureHandlerService, public authService:AuthService) { }



  get isAdmin(){
    return this.authService.loggedInUser?.role===Roles.ADMIN;
  }

  ngOnInit(): void {
  }

  async onAddComment(target:HTMLInputElement):Promise<void>{
    await this.pictureService.addComment(this.post.id,{userName:this.authService.loggedInUser!.name, text:target.value});
    target.value='';
    target.blur();
  }

  async onTitleSubmit(target:HTMLInputElement):Promise<void>{
    if(target.value!==''){
      await this.pictureService.editTitle(this.post.id,target.value);
      target.value='';
    }
    target.blur();
    this.titleIsEdit=false;
  }


  onEdit():void{
    this.titleIsEdit=true;
  }

  onDelete():void{
    this.pictureService.deletePost(this.post.id);
  }

}
