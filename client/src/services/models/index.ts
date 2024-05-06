import httpClient from "../httpClient";
import { type Model, InputCreateModel } from "@/models/model";

const base = 'models'

async function getModels() {
  return await httpClient.get<Model[]>(base);
}

async function createModel(input: InputCreateModel) {
  return await httpClient.post<Model>(base, input);
}

async function deleteModel(id: number) {
  return await httpClient.delete<boolean>(`${base}/${id}`);
}

async function updateModel(id: number, input: Partial<InputCreateModel>) {
  return await httpClient.put<Model>(`${base}/${id}`, input);
}

export default {
  getModels,
  createModel,
  deleteModel,
  updateModel
};