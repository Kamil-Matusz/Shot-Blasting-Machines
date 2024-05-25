import type { Client, InputCreateClient } from "@/models/client";
import httpClient from "../httpClient";
import  { type PaginationParams } from "@/models/paginationParams";

const base = 'clients';

async function getClients(pagination: PaginationParams) {
  return await httpClient.get<Client[]>(base, {params: pagination});
}

async function createClient(client: InputCreateClient) {
  return await httpClient.post<Client>(base, client);
}

async function updateClient(client: Client) {
  return await httpClient.put<Client>(`${base}/${client.id}`, client);
}

async function deleteClient(id: number) {
  return await httpClient.delete<void>(`${base}/${id}`);
}

export default {
  getClients,
  createClient,
  updateClient,
  deleteClient
};
