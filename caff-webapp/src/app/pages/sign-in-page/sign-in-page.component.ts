import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in-page',
  templateUrl: './sign-in-page.component.html',
  styleUrls: ['./sign-in-page.component.scss']
})
export class SignInPageComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  onLogin():void{
    //Validaciok, meg minden
    this.router.navigate(['auth', 'home']);
  }

}
