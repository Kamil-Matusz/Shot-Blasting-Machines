import type { Model } from './model';
import type { Accessory } from './accessory';

export default interface Machine {
    id: number
    model: Model
    accessories: Accessory[]
}