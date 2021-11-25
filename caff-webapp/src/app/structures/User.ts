export enum Roles{
    BASIC, ADMIN
}

export class User{
    constructor(
        public id:string,
        public name:string,
        public email:string,
        public role:Roles
    ){}
}