import { Component, OnInit } from '@angular/core';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';
import { Post } from 'src/app/structures/Post';

@Component({
  selector: 'app-browse-page',
  templateUrl: './browse-page.component.html',
  styleUrls: ['./browse-page.component.scss']
})
export class BrowsePageComponent implements OnInit {

  constructor(public pictureService:PictureHandlerService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  async loadPosts():Promise<void>{
    this.pictureService.isLoading=true;
    await this.pictureService.getFileList();
    this.pictureService.isLoading=false;
  }

}
