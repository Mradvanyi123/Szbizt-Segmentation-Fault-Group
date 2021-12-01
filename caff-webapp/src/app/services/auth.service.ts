import { Injectable } from '@angular/core';
import { User } from '../structures/User';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static loggedInUser:User|undefined;
  static token:string = '';


  public get isLoggedIn() : boolean {
    return AuthService.loggedInUser!==undefined && AuthService.loggedInUser!== null;
  }

  constructor(private httpService:HttpService) { }

  async login(username:string, password:string):Promise<string>{
    try {
      let userResponse = await this.httpService.login(username, password);
      AuthService.loggedInUser = userResponse;
      return '';
    } catch (error:any) {
      return this.httpService.handleError(error);
    }
  }

  async signUp(username:string, email:string, password:string):Promise<string>{
    try {
      await this.httpService.register(username, email, password);
      return '';
    } catch (error:any) {
      return this.httpService.handleError(error);
    }
  }

  async logout():Promise<void>{
    await this.httpService.logout();
    AuthService.token='';
    AuthService.loggedInUser = undefined;
  }

  async getUsers():Promise<User[]>{
    return this.httpService.getUsers();
  }
}
