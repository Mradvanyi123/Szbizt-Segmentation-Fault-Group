import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { AuthService } from "./services/auth.service";
import { Roles } from "./structures/User";

@Injectable()
export class CanActivateUser implements CanActivate{

    constructor(private authService:AuthService, private router:Router){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        if(this.authService.isLoggedIn){
            return true;
        }
        return this.router.createUrlTree(['/signin']);
    }
}

@Injectable()
export class CanActivateAdmin implements CanActivate{

    constructor(){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        
        if(AuthService.loggedInUser?.role===Roles.ADMIN){
            return true;
        }
        return false;
    }
}