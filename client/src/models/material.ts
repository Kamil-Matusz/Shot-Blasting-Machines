export interface Material {
    id:number
    name:string
    price:number
    amount:number
}

export class InputCreateMaterial {
    name:string = ""
    price:number = 0
    amount:number = 0
}