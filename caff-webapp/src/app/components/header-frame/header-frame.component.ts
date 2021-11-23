import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PostAddComponent } from 'src/app/components/post-page/post-add.component';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header-frame',
  templateUrl: './header-frame.component.html',
  styleUrls: ['./header-frame.component.scss']
})
export class HeaderFrameComponent implements OnInit {

  constructor(private router:Router, public dialog:MatDialog, private authService:AuthService) { }


  public get userName() : string {
    return this.authService.loggedInUser!.name
  }

  public get userEmail() : string {
    return this.authService.loggedInUser!.email
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

  onLogout():void{
    //Discard token etc...
    this.router.navigate(['signin']);
  }

  onAdd():void{

  }

}
