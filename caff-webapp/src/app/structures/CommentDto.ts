import { User } from "./User";

export class CommentDto{
    constructor(
        public id:string,
        public user:User,
        public comment:string,
    ){}
}