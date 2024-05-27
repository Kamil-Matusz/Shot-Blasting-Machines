import materialsStoreController from "./materials";
import modelsStoreController from "./models";
import usersStoreController from "./users";
import rolesStoreController from "./roles";
import jwtStoreController from "./jwt";
import ordersStoreController from "./orders";


export const API = {
  materials: materialsStoreController,
  models: modelsStoreController,
  users: usersStoreController,
  roles: rolesStoreController,
  orders: ordersStoreController,
  jwt: jwtStoreController
};