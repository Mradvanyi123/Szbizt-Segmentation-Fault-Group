import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MyErrorStateMatcher } from 'src/app/Utils/MyErrorStatematcher';


@Component({
  selector: 'app-sign-in-page',
  templateUrl: './sign-in-page.component.html',
  styleUrls: ['./sign-in-page.component.scss']
})
export class SignInPageComponent implements OnInit {


  userNameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();
  constructor(private router:Router, private authService:AuthService) { }

  ngOnInit(): void {
  }
  
  async onLogin():Promise<void>{
    let ret:boolean = false;
    if(!this.userNameControl.valid){
      this.userNameControl.markAsDirty();
      ret=true;
    }
    if(!this.passwordControl.valid){
      this.passwordControl.markAsDirty();
      ret = true;
    }
    if(ret) return;
    
    //Validaciok, meg minden
    await this.authService.login(this.userNameControl.value, this.passwordControl.value);
    this.router.navigate(['auth', 'home']);
  }

}
