export interface Model {
    id:number
    name:string
    price:number
    comments:string
}

export class InputCreateModel {
    name:string = ""
    price:number = 0
    comments:string = ""
}