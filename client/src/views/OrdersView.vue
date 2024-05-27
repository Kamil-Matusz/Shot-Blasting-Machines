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

type ReadonlyHeaders = VDataTable['$props']['headers']

const ordersStore = useOrdersStore();
const toast = useToast();

const headers: ReadonlyHeaders = [
  {
    title: 'Id zamówienia',
    align: 'start',
    sortable: false,
    key: 'id',
    width: '50px'
  },
  { title: 'Data złożenia', key: 'date', align: 'start', width: "200px" },
  { title: 'Urządzenie', key: 'machine', align: 'start', width: "200px" },
  { title: 'Klient', key: 'client', align: 'start' },
  { title: 'Status zamówienia', key: 'state', align: 'end', width: "200px" },
  { title: 'Cena', key: 'price', align: 'end', width: "200px" },
  { title: 'Akcje', key: 'actions', align: 'end', width: "50px" },
]

const options = ref({
  page: 1,
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
})

onMounted(async () => {
  await ordersStore.dispatchGetOrders({ page: options.value.page, size: options.value.itemsPerPage });
})

</script>

<template>
  <BasePage title="Zamówienia">
    <template #above-card>
      <v-btn to="/orders/create" color="primary" variant="flat" style="width: 20rem" class="mb-4">+Dodaj nowe
        zamówienie</v-btn>
    </template>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="ordersStore.orders"
      :items-length="options.totalItems" :loading="options.loading" item-value="name">
      <template v-slot:item.machine="{ item }" dense>
        Urządzenie {{ item.machine.model.name }} nr <span class="font-weight-bold">{{ item.machine.id }}</span>
      </template>
      <template v-slot:item.state="{ item }" dense>
        <v-chip color="primary">{{ item.state }}</v-chip>
      </template>
      <template v-slot:item.price="{ item }" dense>
        {{ item.price }} PLN
      </template>
      <template v-slot:item.client="{ item }" dense>
        {{ item.client.name }}
      </template>
      <template v-slot:item.date="{ item }" dense>
        {{ item.date.toString().replace("T", ' ') }}
      </template>
      <template v-slot:item.actions="{ item }" dense>
        <v-btn rounded="lg" size="small" color="primary" class="ml-2" :to="`orders/${item.id}`" >Szczegóły</v-btn>
      </template>
    </v-data-table-server>
  </BasePage>
</template>

<style>
tr:hover {
  background-color: #303030;
  transition: .5s;
}
</style>
