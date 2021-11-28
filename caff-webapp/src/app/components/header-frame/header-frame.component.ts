import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PostAddComponent } from 'src/app/components/post-page/post-add.component';
import { AuthService } from 'src/app/services/auth.service';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Roles } from 'src/app/structures/User';

@Component({
  selector: 'app-header-frame',
  templateUrl: './header-frame.component.html',
  styleUrls: ['./header-frame.component.scss']
})
export class HeaderFrameComponent implements OnInit {

  constructor(private router:Router, public dialog:MatDialog, public authService:AuthService, private pictureService:PictureHandlerService) { }

  public get userName() : string {
    return AuthService.loggedInUser!.username
  }

  public get userEmail() : string {
    return AuthService.loggedInUser!.email
  }

  public get roleText() : string {
    return AuthService.loggedInUser?.role===Roles.ADMIN?'Admin':'Basic user'
  }

  public get isAdmin() : boolean {
    return AuthService.loggedInUser?.role===Roles.ADMIN;
  }


  ngOnInit(): void {
  }

  openDialog(): void {
    this.dialog.open(PostAddComponent, {
      width: '300px',
    });
  }

  onEditProfile():void{
    this.router.navigate(['auth', 'profile']);
  }

  onTitle():void{
    this.router.navigate(['auth','home']);
  }

  onUsers():void{
    this.router.navigate(['auth','users']);
  }

  onLogout():void{
    //Discard token etc...
    this.router.navigate(['signin']);
  }

  async onSearchSubmit(value:string):Promise<void>{
    this.pictureService.isLoading = true;
    await this.pictureService.searchPost(value);
    this.pictureService.isLoading = false;
  }

}
