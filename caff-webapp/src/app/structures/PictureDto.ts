import { Byte } from "@angular/compiler/src/util";
import { Post } from "./Post";

export class PictureDto{
    constructor(
        public id:number,
        public userName:string,
        public pictureName:string,
        public content:Byte[]
    ){}

    public toModel():Post{
        return new Post(this.id,this.pictureName,this.userName,this.content, []);
    }

}