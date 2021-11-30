import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { PictureHandlerService } from 'src/app/services/picture-handler.service';

@Component({
  selector: 'app-post-add',
  templateUrl: './post-add.component.html',
  styleUrls: ['./post-add.component.scss']
})
export class PostAddComponent implements OnInit {

  @ViewChild('fileInput') fileInput:ElementRef<HTMLInputElement> | undefined;
  file: File | null = null;

  title:string = '';

  errorMsg:string = '';

  isLoading:boolean = false;
  constructor(public dialogRef: MatDialogRef<PostAddComponent>, public pictureService:PictureHandlerService) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClickFileInputButton(): void {
    this.fileInput?.nativeElement.click();
  }

  onChangeFileInput(): void {
    const files:FileList | null | undefined = this.fileInput?.nativeElement.files;
    if(files) this.file = files[0];
  }

  async onUpload():Promise<void>{
    if(this.file && this.title!==''){
      this.isLoading=true;
      this.errorMsg = await this.pictureService.uploadFile(this.title, this.file);
      if(this.errorMsg==='')
      this.onNoClick();
      this.isLoading=false;
    }
  }

}
