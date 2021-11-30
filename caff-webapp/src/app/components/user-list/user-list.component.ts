import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Roles, User } from 'src/app/structures/User';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users:User[]=[]
  constructor(private authService:AuthService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  async loadUsers():Promise<void>{
    this.users=await this.authService.getUsers();
  }

  getRoleText(user:User) : string {
    return user.role===Roles.ADMIN?'Admin':'Basic user'
  }

}
