import { Injectable } from '@angular/core';
import { Roles, User } from '../structures/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedInUser:User|undefined = {name: 'Jani', email:'asd@asd.com', id: '0', role:Roles.BASIC};


  public get isLoggedIn() : boolean {
    return this.loggedInUser!==undefined || this.loggedInUser!== null;
  }


  constructor() { }


  async login(username:string, password:string):Promise<User>{
    //http request
    this.loggedInUser = new User('0', 'Test User','asd@asd.com', Roles.BASIC);
    return this.loggedInUser;;
  }

  async logout():Promise<void>{
    this.loggedInUser = undefined;
  }
}
