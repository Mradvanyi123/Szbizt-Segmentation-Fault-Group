import { Byte } from "@angular/compiler/src/util";
import { CommentDto } from "./CommentDto";
import { Post } from "./Post";

export interface userDto{
    id:string,
    email:string,
    username:string
}

export class PictureDto{
    constructor(
        public id:string,
        public user:userDto,
        public pictureName:string,
        public content:Byte[],
        public comments:CommentDto[]
    ){}

    public toModel():Post{
        return new Post(this.id,this.pictureName,this.user.username,this.content, this.comments.map((c)=>{return {text:c.comment, userName:c.user.username};}));
    }

}