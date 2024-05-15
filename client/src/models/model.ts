import type { Material } from "./material"

export interface Model {
    id:number
    name:string
    price:number
    comments:string
    neededMaterials: Material[]
}

export class InputCreateModel {
    name:string = ""
    price:number = 0
    comments:string = ""
    neededMaterials: Material[] = []
}