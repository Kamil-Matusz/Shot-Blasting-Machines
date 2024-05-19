import type { Client } from "./client"
import type { Machine } from "./machine"
import type { User } from "./user"

export interface Order {
    id:number
    price:number
    date:Date
    comments:string
    client:Client
    user:User
    machine:Machine
}

export class InputCreateOrder {
    price:number = 0.0
    date:Date = new Date()
    comments:string = ""
    client:number = 0
    user:number = 0
    model:number = 0
    accessoriers:number[] = []
}