import httpClient from "../httpClient";
import { type Order, InputCreateOrder } from "@/models/order";

const base = 'orders'

async function createOrder(input: InputCreateOrder) {
  return await httpClient.post<Order>(base, input);
}

export default {
    createOrder
};

