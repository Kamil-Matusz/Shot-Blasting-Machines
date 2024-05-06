import httpClient from "../httpClient";
import { type Material, InputCreateMaterial } from "@/models/material";

const base = 'materials'

async function getMaterials() {
  return await httpClient.get<Material[]>(base);
}

async function createMaterial(input: InputCreateMaterial) {
  return await httpClient.post<Material>(base, input);
}

async function deleteMaterial(id: number) {
  return await httpClient.delete<boolean>(`${base}/${id}`);
}

//Increase Material Amount
async function addMaterial(id: number, input: number) {
  return await httpClient.patch<Material>(base, input);
}

//Decrease Material Amount
async function takeMaterial(id: number, input: number) {
  return await httpClient.patch<Material>(base, input);
}

export default {
  getMaterials,
  createMaterial,
  deleteMaterial,
  addMaterialAmount: addMaterial,
  takeMaterial
};