import { type PaginationParams } from '@/models/paginationParams'
import httpClient from '../httpClient'
import { type User, InputCreateUser } from '@/models/user'
import { InputPasswordChange } from '@/models/user'

const base = 'users'

async function getPaginatedUsers(pagination: PaginationParams) {
  return await httpClient.get<User[]>(base, { params: pagination })
}

async function createUser(input: InputCreateUser) {
  return await httpClient.post<User>(base, input)
}

async function deleteUser(id: number) {
  return await httpClient.delete<boolean>(`${base}/${id}`)
}

async function updateUser(input: InputCreateUser, id: number) {
  return await httpClient.put<User>(`${base}/${id}`, input)
}

async function updatePassword(input: InputPasswordChange, id: number) {
  return await httpClient.put<User>(`${base}/${id}/password`, input)
}

export default {
  getPaginatedUsers,
  createUser,
  deleteUser,
  updateUser,
  updatePassword
}
