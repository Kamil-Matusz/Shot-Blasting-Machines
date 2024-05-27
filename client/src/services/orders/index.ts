import httpClient from "../httpClient";
<<<<<<< HEAD
import type { Order } from '../../models/order';
import type { OrderParams } from '../../models/orderParams';

const base = 'orders'

async function getOrders(params: OrderParams) {
  return await httpClient.get<Order[]>(base, { params });
}
async function getOrderById(id: number) {
  return await httpClient.get<Order>(`${base}/${id}`);
}

async function createOrder(input: InputCreateOrder) {
  return await httpClient.post<Order>(base, input);
}



export default {
  createOrder,
  getOrders,
  getOrderById
};
