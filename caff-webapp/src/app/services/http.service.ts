import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommentDto } from '../structures/CommentDto';
import { PictureDto } from '../structures/PictureDto';
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
      let user = await this.http.get<User>(this.basePath+'user', {headers: new HttpHeaders().set('Authorization', `Bearer ${token.token}`)}).toPromise();
      AuthService.token = token.token;
      if(user.role.toString()==='USER')user.role=Roles.USER 
      else user.role=Roles.ADMIN;
      return user;
    }catch(error:any){
      throw error;
    }
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

  public handleError(error:any):string{
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
