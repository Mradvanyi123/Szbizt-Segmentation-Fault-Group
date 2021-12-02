import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FullImageDialogData } from '../post/post.component';

@Component({
  selector: 'app-full-image-display',
  templateUrl: './full-image-display.component.html',
  styleUrls: ['./full-image-display.component.scss']
})
export class FullImageDisplayComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: FullImageDialogData,
    private httpClient: HttpClient
  ) { }

  ngOnInit(): void {
  }

  downloadImage(img:HTMLImageElement){
    const imgUrl:string = img.src;
    this.httpClient.get(imgUrl, {responseType: 'blob' as 'json'}).subscribe((r:any)=>{
      const file:Blob = new Blob([r], {type: r.type});

      const downloadUrl:string = window.URL.createObjectURL(file);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = `${this.data.title}.bmp`;
      link.dispatchEvent(new MouseEvent('click', {
        bubbles: true,
        cancelable: true,
        view: window
      }));

      setTimeout(() => { // firefox
        window.URL.revokeObjectURL(downloadUrl);
        link.remove();
      }, 100);
    });
  }
}
