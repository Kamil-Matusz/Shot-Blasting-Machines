import { Order } from '../../models/order';
<script setup lang="ts">
import { Order } from '@/models/order';
import { useOrdersStore } from '@/stores/ordersStore';
import { onMounted, ref } from 'vue';
import { useToast } from 'vue-toastification';
import OrderState from '../../models/orderStates';
import OrderPdfPreview from '@/components/orders/OrderPdfPreview.vue';


const props = defineProps<{
    state: OrderState
}>();

const order = defineModel<Order>();
const orderStore = useOrdersStore();
const orders = ref<Array<Order>>([]);


onMounted(async () => {
    orders.value = await orderStore.dispatchGetOrders({ stateId: props.state, page: 1, size: 100 })
})


function itemProps(item: any | Order) {
    return {
        title: `Zamówienie ${item.id}`,
        subtitle: `Zamówione przez ${item.client.name} dnia ${item.date.replace(/T.*/, '')}`,
    }
}


</script>

<template>
    <v-select :item-props="itemProps" v-model="order" label="Wybierz zamówienie" variant="outlined" :items="orders">
        <template v-slot:item="{ props, item }">
            <v-list-item v-bind="props">
                <template v-slot:append>
                    <v-dialog max-width="500">
                        <template v-slot:activator="{ props: activatorProps }">
                            <v-btn v-bind="activatorProps" rounded="lg" @click.stop icon="mdi-file" color="primary"
                                variant="text" size="small"></v-btn>
                        </template>
                        <template v-slot:default="{ isActive }">
                            <v-card>
                                <OrderPdfPreview :order-id="(item.raw as Order).id"></OrderPdfPreview>
                            </v-card>
                        </template>
                    </v-dialog>
                </template>
            </v-list-item>
        </template>
    </v-select>
</template>