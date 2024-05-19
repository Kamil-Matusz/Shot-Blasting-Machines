import type { Accessory } from "./accessory"
import type { Model } from "./model"

export interface Machine {
    id:number
    model:Model
    accesories:Accessory[]
}