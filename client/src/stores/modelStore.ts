import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { type Model, InputCreateModel } from '@/models/model';

export const useModelStore = defineStore("modelsStore", () => {
  const models = ref<Model[]>([]);

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  function addNewModel(model: Model) {
    models.value.push(model);
  }

  function removeModel(id: number) : boolean {
    const idx = models.value.findIndex((s) => s.id === id);
    if (idx === -1) return false;
    models.value.splice(idx, 1);
    return true;
  }

  function updateModel(updatedModel: Model) : boolean {
    const existingModelIndex = models.value.findIndex(m => m.id === updatedModel.id);
  
    if (existingModelIndex !== -1) {
      models.value[existingModelIndex] = updatedModel;
      return true
    } 
    return false;
  }

  //Functions that operates on api

  async function dispatchGetModels() {
    const { data } = await API.models.getModels();
    models.value = data;
  }

  async function dispatchCreateModel(input:InputCreateModel) {
    const { data } = await API.models.createModel(input);
    addNewModel(data);
  }

  async function dispatchDeleteModel(id:number) {
    await API.models.deleteModel(id);
    removeModel(id);
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