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
    return this.authService.loggedInUser!.name
  }

  public get userEmail() : string {
    return this.authService.loggedInUser!.email
  }

  public get roleText() : string {
    return this.authService.loggedInUser?.role===Roles.ADMIN?'Admin':'Basic user'
  }

  public get adminRole() : Roles {
    return Roles.ADMIN;
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

  onSearchSubmit(value:string):void{
     this.pictureService.searchPost(value);
  }

}
