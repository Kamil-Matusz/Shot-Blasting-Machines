import httpClient from "../httpClient";
import type { InputCreateOrder, Order } from '../../models/order';
import type { OrderParams } from '../../models/orderParams';

const base = 'orders'

async function getOrders(params: OrderParams) {
  return await httpClient.get<Order[]>(base, { params });
}
async function getOrderById(id: number) {
  return await httpClient.get<Order>(`${base}/${id}`);
}
async function getOrderPDF(id: number) {
  return await httpClient.get<Blob>(`${base}/${id}/summary`,{
    responseType: 'blob'
  });
}
async function createOrder(input: InputCreateOrder) {
  return await httpClient.post<Order>(base, input);
}
async function startProduction(orderId: number) {
  return await httpClient.post<Order>(`${base}/${orderId}/start-production`);
}
async function sendToClient(orderId: number) {
  return await httpClient.post<Order>(`${base}/${orderId}/send-to-client`);
}
async function toCheck(orderId: number) {
  return await httpClient.post<Order>(`${base}/${orderId}/to-check`);
}
async function qualityConfirm(orderId: number) {
  return await httpClient.post<Order>(`${base}/${orderId}/quality-confirm`);
}
async function qualityDecline(orderId: number) {
  return await httpClient.post<Order>(`${base}/${orderId}/quality-decline`);
}

export default {
  createOrder,
  getOrders,
  getOrderById,
  getOrderPDF,
  startProduction,
  sendToClient,
  toCheck,
  qualityConfirm,
  qualityDecline
};
