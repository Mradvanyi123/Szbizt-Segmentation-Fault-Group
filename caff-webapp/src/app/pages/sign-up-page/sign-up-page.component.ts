import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MyErrorStateMatcher } from 'src/app/Utils/MyErrorStatematcher';

@Component({
  selector: 'app-sign-up-page',
  templateUrl: './sign-up-page.component.html',
  styleUrls: ['./sign-up-page.component.scss']
})
export class SignUpPageComponent implements OnInit {

  userNameControl = new FormControl('', [Validators.required]);
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required]);
  password2Control = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();

  errorMsg='';

  constructor(private router:Router, private authService:AuthService) { }

  ngOnInit(): void {
  }

  async onSignUp():Promise<void>{
    let ret:boolean = false;
    if(!this.userNameControl.valid){
      this.userNameControl.markAsDirty();
      ret = true;
    }
    if(!this.emailControl.valid){
      this.emailControl.markAsDirty();
      ret = true;
    }
    if(!this.passwordControl.valid){
      this.passwordControl.markAsDirty();
      ret = true;
    }
    if(!this.password2Control.valid){
      this.password2Control.markAsDirty();
      ret = true;
    }
    if(this.password2Control.value!==this.passwordControl.value){
      this.passwordControl.markAsDirty();
      this.password2Control.markAsDirty();
      ret = true;
      this.errorMsg = "Passwords don't match."
    }
    if(ret) return;

    await this.authService.signUp(this.userNameControl.value, this.emailControl.value, this.passwordControl.value);
    this.router.navigate(['signin']);
  }

}
