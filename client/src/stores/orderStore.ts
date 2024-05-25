import { defineStore } from 'pinia'
import { API } from '../services'
import { type Order, InputCreateOrder } from '@/models/order'

export const useOrderStore = defineStore('ordersStore', () => {
  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  //   function addNewOrder(order: Order) {
  //     orders.value.push(order);
  //   }

  //Functions that operates on api

  async function dispatchCreateOrder(input: InputCreateOrder) {
    const { data } = await API.orders.createOrder(input)
  }

  return {
    dispatchCreateOrder
  }
})
