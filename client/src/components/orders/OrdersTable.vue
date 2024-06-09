<script setup lang="ts">
import { useOrdersStore } from '@/stores/ordersStore';
import { ref, onMounted } from 'vue';
import { useToast } from 'vue-toastification';
import { VDataTable } from 'vuetify/lib/components/index.mjs';
import OrderPdfPreview from '@/components/orders/OrderPdfPreview.vue';

const props = defineProps<{ stateId?: number }>()

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
    { title: 'Akcje', key: 'actions', align: 'end', width: "250px" },
]

const options = ref({
    page: 1,
    itemsPerPage: 10,
    totalItems: 10,
    loading: false
})

onMounted(async () => {
    await ordersStore.dispatchGetOrders({ page: options.value.page, size: options.value.itemsPerPage, stateId:props.stateId });
})

const getColor = (state: string) => {
    switch (state) {
        case "Zakończone":
            return 'green'
        case "Do poprawy":
            return 'red'
        case "W produkcji":
            return 'primary'
        case "Do sprawdzenia":
            return 'orange'
        case "Nowe":
            return 'gray'
        case "Gotowe":
            return 'blue'
    }
}
</script>


<template>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="ordersStore.orders"
        :items-length="options.totalItems" :loading="options.loading" item-value="name">
        <template v-slot:item.machine="{ item }" dense>
            Urządzenie {{ item.machine.model.name }} nr <span class="font-weight-bold">{{ item.machine.id }}</span>
        </template>
        <template v-slot:item.state="{ item }" dense>
            <v-chip :color="getColor(item.state)">{{ item.state }}</v-chip>
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
            <v-btn rounded="lg" color="primary" class="mr-2" variant="outlined" :to="`orders/${item.id}`">Szczegóły</v-btn>
            <v-dialog max-width="500">
                <template v-slot:activator="{ props: activatorProps }">
                    <v-btn v-bind="activatorProps" color="primary" rounded="lg" size="small" icon="mdi-file"
                        variant="flat"></v-btn>
                </template>
                <template v-slot:default="{ isActive }">
                    <v-card>
                        <OrderPdfPreview :order-id="item.id"></OrderPdfPreview>
                    </v-card>
                </template>
            </v-dialog>
        </template>
        <template v-if="stateId" #bottom>

        </template>
    </v-data-table-server>
</template>