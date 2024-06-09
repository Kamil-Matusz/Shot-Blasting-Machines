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

export class InputEditModel {
    name: string = "Nazwa";
    price: number = 0;
    comments: string = "Uwagi";
    neededMaterials: { id: number, amount: number, material: Material}[] = [];

    constructor(model?: Model) {
        if (model) {
            this.name = model.name;
            this.price = model.price;
            this.comments = model.comments;
            // Extracting only id and amount from each needed material
            this.neededMaterials = model.neededMaterials.map(material => ({
                id: material.id,
                amount: material.amount,
                material: material.material
            }));
        }
    }
}

