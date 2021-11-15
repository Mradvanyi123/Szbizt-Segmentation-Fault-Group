import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-frame',
  templateUrl: './header-frame.component.html',
  styleUrls: ['./header-frame.component.scss']
})
export class HeaderFrameComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
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
    this.router.navigate(['auth','post']);
  }

}
