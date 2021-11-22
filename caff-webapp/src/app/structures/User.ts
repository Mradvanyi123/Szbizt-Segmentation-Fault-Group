export enum Roles{
    BASIC, ADMIN
}

export class User{
    constructor(
        public id:string,
        public name:string,
        public role:Roles
    ){}
}