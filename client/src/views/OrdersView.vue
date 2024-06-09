<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useMaterialsStore } from '../stores/materialStore';
import type { VDataTable } from 'vuetify/components'
import { reactive, ref, onMounted, createApp } from 'vue';
import { InputCreateMaterial } from '../models/material';
import MaterialForm from '@/components/materials/MaterialForm.vue';
import { useToast } from "vue-toastification";
import { useOrdersStore } from '../stores/ordersStore';
import orders from '@/services/orders';
import OrderPdfPreview from '@/components/orders/OrderPdfPreview.vue'
import OrderState from '@/models/orderState';
import OrdersTable from '@/components/orders/OrdersTable.vue';



</script>

<template>
  <BasePage title="Zamówienia">
    <template #above-card>
      <v-btn to="/orders/create" color="primary" variant="flat" style="width: 20rem" class="mb-4">+Dodaj nowe
        zamówienie</v-btn>
    </template>
    <OrdersTable></OrdersTable>
  </BasePage>
</template>

<style>
tr:hover {
  background-color: #303030;
  transition: .5s;
}
</style>
