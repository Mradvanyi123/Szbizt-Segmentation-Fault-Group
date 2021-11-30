import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { PictureDto } from '../structures/PictureDto';
import { IComment, Post } from '../structures/Post';
import { AuthService } from './auth.service';
import { HttpService } from './http.service';
import { MOCK_POSTS } from './mock';

@Injectable({
  providedIn: 'root'
})
export class PictureHandlerService {

  constructor(private httpService:HttpService) { }

  posts:Post[] = MOCK_POSTS;

  isLoading:boolean = false;

  async uploadFile(title:string, file:File):Promise<string>{
    try {
      let newPost = await this.httpService.postPicture(title, file);
      this.posts.unshift({id:newPost.id,
        img:newPost.content, 
        title:newPost.name, 
        userName:newPost.user.username,
        comments:newPost.comments?newPost.comments.map(c=>{return {text:c.comment, userName:c.user.username};}):[]
      });
      return '';
    } catch (error:any) {
      let msg = this.httpService.handleError(error);
      return msg;
    }

  }

  async getFileList():Promise<void>{
    //HTTP get all pictures
    //await new Promise(f => setTimeout(f, 1000));
    this.posts = await this.httpService.getPictures();
    //this.posts = MOCK_POSTS;
    //return this.posts
  }

  async searchPost(keyword:string):Promise<void>{
    this.posts = await this.httpService.searchPictures(keyword);
  }

  async addComment(postId:string, comment:IComment):Promise<void>{
    //await new Promise(f => setTimeout(f, 1000));
    try {
      let newComment:IComment = await this.httpService.postComment(postId, comment.text);
      this.posts.find(p=>p.id===postId)?.comments.push(newComment);
    } catch (error:any) {
      this.httpService.handleError(error);
    }
    
  }

  async editTitle(postId:string, newTitle:string):Promise<void>{
    try {
      let pic:PictureDto = await this.httpService.editPictureName(newTitle, postId);
      let post:Post|undefined = this.posts.find(p=>p.id===pic.id);
      if(post)
        post.title = pic.name;
    } catch (error:any) {
      this.httpService.handleError(error);
    }
  }

  async deletePost(postId:string):Promise<void>{
    try {
      await this.httpService.deltePicture(postId);
    } catch (error:any) {
      this.httpService.handleError(error);
    }
    let idx:number = this.posts.findIndex(p=>p.id===postId);
    this.posts.splice(idx,1);
  }
}
