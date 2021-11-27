import { Byte } from "@angular/compiler/src/util";

export interface IComment{
    userName:string,
    text:string,
}

export class Post{
    constructor(
        public id:number,
        public title:string,
        public userName:string,
        public img:Byte[] | string,
        public comments:IComment[]
    ){}
}