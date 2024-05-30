import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import type { Order } from '../models/order';
import type { OrderParams } from '../models/orderParams';

export const useOrdersStore = defineStore("ordersStore", () => {
    const orders = ref<Order[]>([]);

    async function dispatchGetOrders(params: OrderParams) {
        const { data } = await API.orders.getOrders(params);
        orders.value = data;
        return orders.value
    }

    async function dispatchGetOrderByID(id: number): Promise<Order> {
        const { data } = await API.orders.getOrderById(id);
        return data
    }

    async function dispatchGetOrderPdfByID(id: number): Promise<Blob> {
        const { data } = await API.orders.getOrderPDF(id);
        return data
    }
    async function dispatchStartProduction(id: number) {
        const { data } = await API.orders.startProduction(id);
    }
    async function dispatchQualityConfirm(id: number) {
        const { data } = await API.orders.qualityConfirm(id);
    }
    async function dispatchQualityDecline(id: number) {
        const { data } = await API.orders.qualityDecline(id);
    }
    async function dispatchSendToClient(id: number) {
        const { data } = await API.orders.sendToClient(id);
    }
    async function dispatchMarkAsToCheck(id: number) {
        const { data } = await API.orders.toCheck(id);
    }

    return {
        orders,
        dispatchGetOrders,
        dispatchGetOrderByID,
        dispatchGetOrderPdfByID,
        dispatchStartProduction,
        dispatchQualityConfirm,
        dispatchQualityDecline,
        dispatchSendToClient,
        dispatchMarkAsToCheck
    };
});