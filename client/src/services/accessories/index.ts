import { type Accessory } from "@/models/accessory";
import httpClient from "../httpClient";

const base = 'accessories';

async function getAccesories() {
  return await httpClient.get<Accessory[]>(base);
}

export default {
    getAccesories
};