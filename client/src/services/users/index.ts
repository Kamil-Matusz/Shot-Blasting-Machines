import httpClient from "../httpClient";
import { type User, InputCreateUser } from "@/models/user";

const base = 'users'

async function getUsers() {
  return await httpClient.get<User[]>(base);
}

async function createUser(input: InputCreateUser) {
  return await httpClient.post<User>(base, input);
}

async function deleteUser(id: number) {
  return await httpClient.delete<boolean>(`${base}/${id}`);
}

async function updateUser(input: InputCreateUser, id: number) {
  return await httpClient.put<User>(`${base}/${id}`, input);
}


export default {
  getUsers,
  createUser,
  deleteUser,
  updateUser
};
