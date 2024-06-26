import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/DashboardView.vue'
import UsersManagementView from '../views/UsersManagementView.vue'
import StoreView from '../views/StoreView.vue'
import OrdersView from '../views/OrdersView.vue'
import ProductionView from '../views/ProductionView.vue'
import ConstructionsView from '../views/ConstructionsView.vue'
import AddOrderView from '@/views/AddOrderView.vue'
import LoginView from '@/views/LoginView.vue'
import OrderDetails from '@/views/OrderDetails.vue'
import ClientView from '@/views/ClientView.vue'
import ProfileView from '@/views/ProfileView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/dashboard',
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
      component: UsersManagementView
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
      path: '/orders/:id',
      name: 'Order',
      component: OrderDetails,
    },
    {
      path: '/constructions',
      name: 'constructions',
      component: ConstructionsView
    },
    {
      path: '/orders/create',
      name: 'add-order',
      component: AddOrderView
    },
    {
      path: '/clients',
      name: 'clients',
      component: ClientView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
  ]
})

export default router
