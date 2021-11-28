import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommentDto } from '../structures/CommentDto';
import { PictureDto, userDto } from '../structures/PictureDto';
import { User } from '../structures/User';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  readonly basePath:string = 'http://localhost:8080/api/';

  constructor(private http:HttpClient) { }

  async login(username:string, password:string){
    let token = await this.http.post<{token:string}>(this.basePath+'login',{username:username, password:password}).toPromise();
    console.log(token);
    let user = await this.http.get<string>(this.basePath+'user', {headers: new HttpHeaders().set('Authorization', `Bearer ${token.token}`)}).toPromise();
    console.log(user);
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
    let params:HttpParams|undefined;
    // if(keyword!==''){
    //   params= 
    // }
    let r = await this.http.get<PictureDto>(this.basePath+'picture', ).toPromise();
    console.log(r);
  }

  async postPicture(){
    let r = await this.http.post<PictureDto>(this.basePath+'picture',PictureDto).toPromise();
    console.log(r);
  }

  async postComment(postId:string){
    let r = await this.http.post<CommentDto>(this.basePath+`picture/${postId}/comment`,CommentDto).toPromise();
    console.log(r);
  }

  async deltePicture(postId:string){
    let r = await this.http.delete<object>(this.basePath+`picture/${postId}`).toPromise();
    console.log(r);
  }
}
