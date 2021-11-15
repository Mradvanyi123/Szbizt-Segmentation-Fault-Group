import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';import { HeaderFrameComponent } from './components/header-frame/header-frame.component';
import { BrowsePageComponent } from './pages/browse-page/browse-page.component';
import { PostPageComponent } from './pages/post-page/post-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { SignInPageComponent } from './pages/sign-in-page/sign-in-page.component';
import { SignUpPageComponent } from './pages/sign-up-page/sign-up-page.component';
;

const routes: Routes = [
  {path:'', redirectTo:'signin', pathMatch: 'full'},
  {path:'signin', component:SignInPageComponent},
  {path:'signup', component:SignUpPageComponent},
  {path:'auth', component:HeaderFrameComponent ,children:[
    {path:'home', component:BrowsePageComponent},
    {path:'profile', component:ProfilePageComponent},
    {path:'post', component:PostPageComponent},
  ]
}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
