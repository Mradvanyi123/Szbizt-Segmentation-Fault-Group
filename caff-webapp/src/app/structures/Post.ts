import { Byte } from "@angular/compiler/src/util";
import { SafeUrl } from "@angular/platform-browser";

export interface IComment{
    userName:string,
    text:string,
}

export class Post{
    constructor(
        public id:string,
        public title:string,
        public userName:string,
        public img: string,
        public comments:IComment[]
    ){}
}