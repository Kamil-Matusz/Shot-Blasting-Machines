import { defineStore } from 'pinia'
import { ref } from 'vue'
import { API } from '../services'
import { type User, InputCreateUser, InputPasswordChange } from '@/models/user'
// @ts-ignore
import { type PaginationParams } from '@/models/paginationParams'

export const useUserStore = defineStore('usersStore', () => {
  const users = ref<User[]>([])
  const totalItems = ref(0)

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  function addNewUser(user: User) {
    users.value.push(user)
  }

  function removeUser(id: number): boolean {
    const idx = users.value.findIndex((s) => s.id === id)
    if (idx === -1) return false
    users.value.splice(idx, 1)
    return true
  }

  function updateUser(updatedUser: User): boolean {
    const existingUserIndex = users.value.findIndex((m) => m.id === updatedUser.id)

    if (existingUserIndex !== -1) {
      users.value[existingUserIndex] = updatedUser
      return true
    }
    return false
  }

  //Functions that operates on api

  async function dispatchGetUsers(pagination: PaginationParams) {
    const { data } = await API.users.getPaginatedUsers(pagination)
    // @ts-ignore
    users.value = data.content
    // @ts-ignore
    totalItems.value = data.totalElements
  }

  async function dispatchCreateUser(input: InputCreateUser) {
    const { data } = await API.users.createUser(input)
    addNewUser(data)
  }

  async function dispatchDeleteUser(id: number) {
    await API.users.deleteUser(id)
    removeUser(id)
  }

  async function dispatchUpdateUser(input: InputCreateUser, id: number) {
    const { data } = await API.users.updateUser(input, id)
    updateUser(data)
  }

  async function dispatchChangePassword(input: InputPasswordChange, id: number) {
    await API.users.updatePassword(input, id)
  }

  return {
    users,
    dispatchCreateUser,
    dispatchGetUsers,
    dispatchDeleteUser,
    dispatchUpdateUser,
    totalItems,
    dispatchChangePassword
  }
})
