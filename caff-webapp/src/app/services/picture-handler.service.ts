import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PictureHandlerService {

  constructor() { }

  uploadFile(fileBytes:string){
    //TODO HTTP service
  }
}
