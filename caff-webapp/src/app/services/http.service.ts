import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { CommentDto } from '../structures/CommentDto';
import { PictureDto } from '../structures/PictureDto';
import { User } from '../structures/User';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  readonly basePath:string = 'http://localhost:8080/api/';

  constructor(private http:HttpClient) { }

  async login(username:string, password:string){
    let r = await this.http.post<User>(this.basePath+'user/login',{username:username, password:password}).toPromise();
    console.log(r);
  }

  async register(username:string,email:string, password:string){
    let r = await this.http.post<User>(this.basePath+'user/register', {username:username, email:email, password:password}).toPromise();
    console.log(r);
  }

  async getUsers(){
    let r = await this.http.get<User[]>(this.basePath+'user').toPromise();
    console.log(r);
  }

  async getPictures(keyword:string){
    let r = await this.http.get<PictureDto>(this.basePath+'picture', {params:{name:keyword}}).toPromise();
    console.log(r);
  }

  async postPicture(){
    let r = await this.http.post<PictureDto>(this.basePath+'picture',PictureDto).toPromise();
    console.log(r);
  }

  async postComment(postId:number){
    let r = await this.http.post<CommentDto>(this.basePath+`picture/${postId}/comment`,CommentDto).toPromise();
    console.log(r);
  }

  async deltePicture(postId:number){
    let r = await this.http.delete<object>(this.basePath+`picture/${postId}`).toPromise();
    console.log(r);
  }
}
