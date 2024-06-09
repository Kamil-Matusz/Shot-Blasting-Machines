import materialsStoreController from "./materials";
import modelsStoreController from "./models";
import usersStoreController from "./users";
import rolesStoreController from "./roles";
import jwtStoreController from "./jwt";
import clientsStoreController from "./clients";
import accessoriesStoreController from "./accessories";
import ordersStoreController from "./orders";
import statsStoreController from "./stats";


export const API = {
  materials: materialsStoreController,
  models: modelsStoreController,
  users: usersStoreController,
  roles: rolesStoreController,
  orders: ordersStoreController,
  stats: statsStoreController,
  jwt: jwtStoreController,
  clients: clientsStoreController,
  accessories: accessoriesStoreController,
};