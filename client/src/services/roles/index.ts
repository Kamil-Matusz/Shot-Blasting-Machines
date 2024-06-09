import httpClient from "../httpClient";
import { type Role } from "@/models/role";

const base = 'roles'

async function getRoles() {
  return await httpClient.get<Role[]>(base);
}

export default {
  getRoles,
};
