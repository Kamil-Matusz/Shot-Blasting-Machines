import materialsStoreController from "./materials";
import modelsStoreController from "./models";
import usersStoreController from "./users";
import rolesStoreController from "./roles";


export const API = {
  materials: materialsStoreController,
  models: modelsStoreController,
  users: usersStoreController,
  roles: rolesStoreController
};