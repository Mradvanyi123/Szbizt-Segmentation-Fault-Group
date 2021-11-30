import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Byte } from '@angular/compiler/src/util';
import { Injectable } from '@angular/core';
import { CommentDto } from '../structures/CommentDto';
import { PictureDto } from '../structures/PictureDto';
import { Post, IComment } from '../structures/Post';
import { Roles, User } from '../structures/User';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  readonly basePath:string = 'http://localhost:8080/api/';

  constructor(private http:HttpClient ) { }

  async login(username:string, password:string):Promise<User>{
    try{
      let token = await this.http.post<{token:string}>(this.basePath+'login',{username:username, password:password}).toPromise();
      AuthService.token = token.token;
      let user = await this.http.get<User>(this.basePath+'user', {headers: this.addAuthHeader()}).toPromise();
      if(user.role.toString()==='USER')user.role=Roles.USER 
      else user.role=Roles.ADMIN;
      return user;
    }catch(error:any){
      throw error;
    }
  }

  private addAuthHeader = ():HttpHeaders=>{
    return new HttpHeaders().set('Authorization', `Bearer ${AuthService.token}`)
  }

  async logout(){
    try{
      await this.http.post(this.basePath+'login',{}, {headers: new HttpHeaders().set('Authorization', `Bearer ${AuthService.token}`)}).toPromise();
    }catch(error:any){
      throw error;
    }
  }

  async register(username:string,email:string, password:string){
    try{
      await this.http.post<User>(this.basePath+'user/register', {username:username, email:email, password:password}).toPromise();
    }catch(error:any){
      throw error;
    }
  }

  async getUsers():Promise<User[]>{
    try {
      let r = await this.http.get<User[]>(this.basePath+'user/all', {headers:this.addAuthHeader()}).toPromise();
      r.forEach(user => {
        if(user.role.toString()==='USER')user.role=Roles.USER 
        else user.role=Roles.ADMIN;
      });
      return r;
    } catch (error:any) {
      throw error;
    }
  }

  async getPictures():Promise<Post[]>{
    let r = await this.http.get<PictureDto[]>(this.basePath+'picture',{headers: this.addAuthHeader()} ).toPromise();
    let posts:Post[] = r.map(r=>new Post(r.id,r.name,r.user.username,r.content, r.comments.map((c)=>{return {text:c.comment, userName:c.user.username};})));
    return posts;
  }

  async searchPictures(keyword:string):Promise<Post[]>{
    let r = await this.http.get<PictureDto[]>(this.basePath+'picture',{headers: this.addAuthHeader(), params:{'name':keyword}}).toPromise();
    let posts:Post[] = r.map(r=>new Post(r.id,r.name,r.user.username,r.content, r.comments.map((c)=>{return {text:c.comment, userName:c.user.username};})));
    return posts;
  }

  async postPicture(title:string, cafFile:File):Promise<PictureDto>{
    let formData: FormData = new FormData();
    formData.append('caffFile', cafFile);
    formData.append('name', title);
    try {
      return await this.http.post<PictureDto>(this.basePath+'picture',formData, {headers:this.addAuthHeader()}).toPromise();
    } catch (error:any) {
      throw error;
    }
  }

  async editPictureName(newTitle:string, pictureId:string):Promise<PictureDto>{
    try {
      return await this.http.put<PictureDto>(this.basePath+`picture/${pictureId}`,newTitle, {headers:this.addAuthHeader()}).toPromise();
    } catch (error:any) {
      throw error;
    }
  }

  async postComment(postId:string, text:string):Promise<IComment>{
    let r = await this.http.post<CommentDto>(this.basePath+`picture/${postId}/comment`,{comment:text}, {headers: this.addAuthHeader()}).toPromise();
    return {text:r.comment, userName:r.user.username};
  }

  async deltePicture(postId:string){
    try {
      await this.http.delete(this.basePath+`picture/${postId}`, {headers:this.addAuthHeader()}).toPromise();
    } catch (error:any) {
      throw error;
    }
  }

  public handleError(error:any):string{
    console.log(error);
    if(error instanceof HttpErrorResponse){
      if(error.status===403){
        return 'Incorrect username or password'
      }
      if(error.status===500){
        return 'Server error'
      }
    }
    return 'Unknown error!';
  }
}
