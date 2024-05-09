import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { type Material, InputCreateMaterial } from '@/models/material';

export const useMaterialsStore = defineStore("materialsStore", () => {
  const materials = ref<Material[]>([]);

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  function addNewMaterial(material: Material) {
    materials.value.push(material);
  }

  function removeMaterial(id: number) : boolean {
    const idx = materials.value.findIndex((s) => s.id === id);
    if (idx === -1) return false;
    materials.value.splice(idx, 1);
    return true;
  }

  function updateMaterial(updatedMaterial: Material) : boolean {
    const existingMaterialIndex = materials.value.findIndex(m => m.id === updatedMaterial.id);
  
    if (existingMaterialIndex !== -1) {
      materials.value[existingMaterialIndex] = updatedMaterial;
      return true
    } 
    return false;
  }

  //Functions that operates on api

  async function dispatchGetMaterials() {
    const { data } = await API.materials.getMaterials();
    materials.value = data;
  }

  async function dispatchCreateMaterial(input:InputCreateMaterial) {
    const { data } = await API.materials.createMaterial(input);
    addNewMaterial(data);
  }

  async function dispatchDeleteMaterial(id:number) {
    await API.materials.deleteMaterial(id);
    removeMaterial(id);
  }

  async function dispatchAddMaterialAmount(id:number,amountToAdd:number) {
    const { data } = await API.materials.addMaterialAmount(id,amountToAdd);
    updateMaterial(data);
  }

  async function dispatchTakeMaterial(id:number,amountToTake:number) {
    const { data } = await API.materials.takeMaterial(id,amountToTake);
    updateMaterial(data);
  }

  return {
    materials,
    dispatchCreateMaterial,
    dispatchGetMaterials,
    dispatchDeleteMaterial,
    dispatchAddMaterialAmount,
    dispatchTakeMaterial
  };
});