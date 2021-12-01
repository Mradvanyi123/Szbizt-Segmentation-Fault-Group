import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/services/auth.service';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Post } from 'src/app/structures/Post';
import { Roles } from 'src/app/structures/User';
import { FullImageDisplayComponent } from '../full-image-display/full-image-display.component';

export interface FullImageDialogData {
  title: string;
  imageData: string;
}

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post:Post | undefined;
  titleIsEdit:boolean = false;

  constructor(private pictureService:PictureHandlerService, public authService:AuthService, public dialog:MatDialog) { }

  isLoading:boolean=false;

  get isAdmin(){
    return AuthService.loggedInUser?.role===Roles.ADMIN;
  }

  ngOnInit(): void {
  }

  async onAddComment(target:HTMLInputElement):Promise<void>{
    this.isLoading=true;
    await this.pictureService.addComment(this.post!.id,{userName:AuthService.loggedInUser!.username, text:target.value});
    this.isLoading=false;
    target.value='';
    target.blur();
  }

  async onTitleSubmit(target:HTMLInputElement):Promise<void>{
    if(target.value!==''){
      await this.pictureService.editTitle(this.post!.id,target.value);
      target.value='';
    }
    target.blur();
    this.titleIsEdit=false;
  }


  onEdit():void{
    this.titleIsEdit=true;
  }

  async onDelete():Promise<void>{
    await this.pictureService.deletePost(this.post!.id);
  }

  onimageClick(){
    this.dialog.open(FullImageDisplayComponent, {
      data:{title:this.post!.title, imageData:this.post!.img}
    });
  }

}
