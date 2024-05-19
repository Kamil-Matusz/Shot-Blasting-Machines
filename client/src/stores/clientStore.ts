import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { type Client } from '@/models/client';

export const useClientsStore = defineStore("clientsStore", () => {
  const clients = ref<Client[]>([]);

  async function dispatchGetClients() {
    const { data } = await API.clients.getClients();
    clients.value = data;
  }


  return {
    dispatchGetClients,
    clients
  };
});
