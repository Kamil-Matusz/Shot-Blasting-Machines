import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import type { Order } from '../models/order';
import type { OrderParams } from '../models/orderParams';

export const useOrdersStore = defineStore("ordersStore", () => {
    const orders = ref<Order[]>([]);

    //Functions that operates on api

    async function dispatchGetOrders(params: OrderParams) {
        const { data } = await API.orders.getOrders(params);
        orders.value = data;
        return orders.value
    }

    async function dispatchGetOrderByID(id: number): Promise<Order> {
        const { data } = await API.orders.getOrderById(id);
        return data
    }

    return {
        orders,
        dispatchGetOrders,
        dispatchGetOrderByID
    };
});