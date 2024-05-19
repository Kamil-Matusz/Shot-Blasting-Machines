import { type Client } from "@/models/client";
import httpClient from "../httpClient";

const base = 'clients'

async function getClients() {
  return await httpClient.get<Client[]>(base);
}

export default {
  getClients
};