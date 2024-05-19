import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { type Accessory } from '@/models/accessory';

export const useAccessoryStore = defineStore("accessoriesStore", () => {
  const accessories = ref<Accessory[]>([]);

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  //Functions that operates on api

  async function dispatchGetAccesories() {
    const { data } = await API.accessories.getAccesories();
    accessories.value = data;
  }


  return {
    dispatchGetAccesories,
    accessories
  };
});
