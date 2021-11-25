import { Injectable } from '@angular/core';
import { IComment, Post } from '../structures/Post';
import { AuthService } from './auth.service';
import { MOCK_POSTS } from './mock';

@Injectable({
  providedIn: 'root'
})
export class PictureHandlerService {

  constructor(private authService:AuthService) { }

  posts:Post[] = MOCK_POSTS;

  uploadFile(title:string, fileBytes:string){
    //TODO HTTP service
    this.posts.unshift({id:Math.floor(Math.random() * 9999).toString(), img:fileBytes, title:title, userName:this.authService.loggedInUser!.name, comments:[] });
  }

  async getFileList():Promise<void>{
    //HTTP get all pictures
    this.posts = MOCK_POSTS;
    //return this.posts
  }

  searchPost(keyword:string):void{
    this.posts = MOCK_POSTS.filter((p)=>p.title.toLowerCase().includes(keyword.toLowerCase()));
    console.log(this.posts);
  }

  async addComment(postId:string, comment:IComment):Promise<void>{
    this.posts.find(p=>p.id===postId)?.comments.push(comment);
  }
}
