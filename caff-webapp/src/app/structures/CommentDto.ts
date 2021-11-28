export class CommentDto{
    constructor(
        public id:string,
        public picture_id:number,
        public user_id:string,
        public comment:string,
    ){}
}