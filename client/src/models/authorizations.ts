export interface LoginData {
    email: string;
    password: string;
}

export class InputLoginData {
    email:string = "";
    password:string = "";
}

export interface JwtToken {
    jwt: string;
}

export class InputJwtToken {
    jwt: string;
    constructor(jwt: string) {
        this.jwt = jwt;
    }
}
