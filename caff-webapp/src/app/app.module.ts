import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PostAddComponent } from './components/post-add/post-add.component';
import { SignInPageComponent } from './pages/sign-in-page/sign-in-page.component';
import { SignUpPageComponent } from './pages/sign-up-page/sign-up-page.component';
import { BrowsePageComponent } from './pages/browse-page/browse-page.component';
import { HeaderFrameComponent } from './components/header-frame/header-frame.component';
import { MatToolbarModule} from '@angular/material/toolbar';
import { MatIconModule} from '@angular/material/icon';
import { MatCardModule} from '@angular/material/card';
import { MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {MatDialogModule} from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import { PostComponent } from './components/post/post.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserListPageComponent } from './pages/user-list-page/user-list-page.component';
import { CanActivateAdmin, CanActivateUser } from './CanActivateRoute';
import { HttpClientModule } from '@angular/common/http';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { FullImageDisplayComponent } from './components/full-image-display/full-image-display.component';

@NgModule({
  declarations: [
    AppComponent,
    PostAddComponent,
    SignInPageComponent,
    SignUpPageComponent,
    BrowsePageComponent,
    HeaderFrameComponent,
    PostComponent,
    UserListPageComponent,
    FullImageDisplayComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatMenuModule,
    MatDialogModule,
    MatExpansionModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatProgressSpinnerModule
  ],
  providers: [CanActivateUser, CanActivateAdmin],
  bootstrap: [AppComponent]
})
export class AppModule { }
