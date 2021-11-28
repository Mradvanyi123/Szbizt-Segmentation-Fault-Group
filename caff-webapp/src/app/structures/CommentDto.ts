import { userDto } from "./PictureDto";

export class CommentDto{
    constructor(
        public id:string,
        public user:userDto,
        public comment:string,
    ){}
}