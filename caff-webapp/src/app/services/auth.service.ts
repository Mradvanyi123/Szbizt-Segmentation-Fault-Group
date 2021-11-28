import { Injectable } from '@angular/core';
import { Roles, User } from '../structures/User';
import { HttpService } from './http.service';
import { MOCK_USERS } from './mock';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedInUser:User|undefined;


  public get isLoggedIn() : boolean {
    return this.loggedInUser!==undefined && this.loggedInUser!== null;
  }

  constructor(private httpService:HttpService) { }

  async login(username:string, password:string):Promise<User>{
    this.httpService.login(username, password);
    this.loggedInUser = MOCK_USERS[1];
    return this.loggedInUser;;
  }

  async signUp(username:string, email:string, password:string):Promise<void>{
    //TODO
  }

  async logout():Promise<void>{
    this.loggedInUser = undefined;
  }

  async getUsers():Promise<User[]>{
    return MOCK_USERS;
  }
}
