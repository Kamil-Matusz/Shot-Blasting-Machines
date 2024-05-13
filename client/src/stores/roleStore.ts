import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { type Role } from '@/models/role';

export const useRoleStore = defineStore("rolesStore", () => {
  const roles = ref<Role[]>([]);

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  //Functions that operates on api

  async function dispatchGetRoles() {
    const { data } = await API.roles.getRoles();
    roles.value = data;
  }


  return {
    dispatchGetRoles,
    roles
  };
});
