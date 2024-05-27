import type Client from "./client"
import type Machine from "./machine"
import { User } from './user';

export interface Order {
    id: number
    price: number
    date: Date
    state: string
    comments: string
    machine: Machine
    client: Client
    user: User
}

