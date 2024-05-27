import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { InputCreateClient, type Client } from '@/models/client';
// @ts-ignore
import { type PaginationParams } from "@/models/paginationParams";

export const useClientsStore = defineStore("clientsStore", () => {
  const clients = ref<Client[]>([]);
  const totalItems = ref(0);

  async function dispatchGetClients(pagination: PaginationParams) {
    try {
      const { data } = await API.clients.getClients(pagination);
    // @ts-ignore
      clients.value = data.content;
    // @ts-ignore
    totalItems.value = data.totalElements;
    } catch (error) {
      console.error("Error fetching clients:", error);
    }
  }

  async function dispatchCreateClient(newClient: InputCreateClient) {
    try {
      const { data } = await API.clients.createClient(newClient);
      clients.value.push(data);
    } catch (error) {
      console.error("Error creating client:", error);
    }
  }

  async function dispatchUpdateClient(updatedClient: Client) {
    try {
      await API.clients.updateClient(updatedClient);
      const index = clients.value.findIndex(client => client.id === updatedClient.id);
      if (index !== -1) {
        clients.value[index] = updatedClient;
      }
    } catch (error) {
      console.error("Error updating client:", error);
    }
  }

  async function dispatchDeleteClient(clientId: number) {
    try {
      await API.clients.deleteClient(clientId);
      clients.value = clients.value.filter(client => client.id !== clientId);
    } catch (error) {
      console.error("Error deleting client:", error);
    }
  }

  return {
    clients,
    dispatchGetClients,
    dispatchCreateClient,
    dispatchUpdateClient,
    dispatchDeleteClient,
    totalItems
  };
});




