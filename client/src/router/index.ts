import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/DashboardView.vue'
import UsersManagmentView from '../views/UsersManagmentView.vue'
import StoreView from '../views/StoreView.vue'
import OrdersView from '../views/OrdersView.vue'
import ProductionView from '../views/ProductionView.vue'
import ConstructionsView from '../views/ConstructionsView.vue'
import AddOrderView from '@/views/AddOrderView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      alias: '/dashboard',
      component: DashboardView
    },
    {
      path: '/store',
      name: 'store',
      component: StoreView
    },
    {
      path: '/users-managment',
      name: 'users-managment',
      component: UsersManagmentView
    },
    {
      path: '/production',
      name: 'production',
      component: ProductionView
    },
    {
      path: '/orders',
      name: 'orders',
      component: OrdersView
    },
    {
      path: '/constructions',
      name: 'constructions',
      component: ConstructionsView
    },
    {
      path: '/add-order',
      name: 'add-order',
      component: AddOrderView
    },
  ]
})

export default router
