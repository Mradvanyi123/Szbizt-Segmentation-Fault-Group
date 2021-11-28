import { Byte } from "@angular/compiler/src/util";
import { CommentDto } from "./CommentDto";
import { Post } from "./Post";
import { User, } from "./User";

export class PictureDto{
    constructor(
        public id:string,
        public user:User,
        public name:string,
        public content:Byte[],
        public comments:CommentDto[]
    ){}

    public toModel():Post{
        return new Post(this.id,this.name,this.user.username,this.content, this.comments.map((c)=>{return {text:c.comment, userName:c.user.username};}));
    }

}