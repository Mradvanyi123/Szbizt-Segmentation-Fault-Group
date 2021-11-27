export interface IComment{
    userName:string,
    text:string,
}

export class Post{
    constructor(
        public id:number,
        public title:string,
        public userName:string,
        public img:string,
        public comments:IComment[]
    ){}
}