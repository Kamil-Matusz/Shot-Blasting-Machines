import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/DashboardView.vue'
import UsersManagmentView from '../views/UsersManagmentView.vue'
import StoreView from '../views/StoreView.vue'
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
      path: '/add-order',
      name: 'add-order',
      component: AddOrderView
    },
    {
      path: '/constructions',
      name: 'ordes',
      component: ConstructionsView
    }
  ]
})

export default router
