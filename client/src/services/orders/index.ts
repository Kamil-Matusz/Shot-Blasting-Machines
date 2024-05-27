import httpClient from "../httpClient";
import type { Order } from '../../models/order';
import type { OrderParams } from '../../models/orderParams';

const base = 'orders'

async function getOrders(params: OrderParams) {
  return await httpClient.get<Order[]>(base, { params });
}
async function getOrderById(id: number) {
  return await httpClient.get<Order>(`${base}/${id}`);
}


export default {
  getOrders,
  getOrderById
};