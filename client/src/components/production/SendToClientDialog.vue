<script setup lang="ts">
import { isReactive, ref, Ref } from 'vue';
import OrderSelect from '../orders/OrderSelect.vue';
import { Order } from '../../models/order';
import OrderState from '@/models/orderState';
import { useOrdersStore } from '../../stores/ordersStore';

const props = defineProps<{
    isActive: Ref<boolean>
}>();

const ordersStore = useOrdersStore();
const isLoading = ref<boolean>(false);
const isSuccesfullyAdded = ref<boolean>(false);
const selectedOrder = ref<Order>();

const sendToClient = async () => {
    isLoading.value = true;
    if (selectedOrder.value?.id)
        await ordersStore.dispatchSendToClient(selectedOrder.value?.id);

    isLoading.value = false;
    isSuccesfullyAdded.value = true;
}

const closeAll = () => {
    isSuccesfullyAdded.value = false;
    props.isActive.value = false;
}
</script>

<template>
    <v-card class="pa-4">
        <h4 class="text-h6 mb-4 side-line">Wysyłka</h4>
        <order-select v-model="selectedOrder" :state="OrderState.READY"></order-select>
        <p>Zamówienie kuriera oraz poinformowanie klienta o dostawie</p>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="!isSuccesfullyAdded" :loading="isLoading" variant="flat" color="primary" text="Wyślij" @click="sendToClient"></v-btn>
        </v-card-actions>
    </v-card>
    <v-dialog max-width="500" v-model="isSuccesfullyAdded">
        <template v-slot:default="{ isActive }">
            <v-card class="pa-4" rounded="lg">
                <div class="d-flex flex-column justify-center align-center">
                    <v-icon size="80" color="primary">mdi-check</v-icon>
                    <p class="py-4 text-center">Pomyślnie zamówiono kuriera oraz poinformowanie klienta o dostawie</p>
                    <div class="d-flex">
                        <v-btn v-if="selectedOrder" variant="flat" color="primary" :to="`/orders/${selectedOrder?.id}`" text="Zobacz szczegóły zamówienia"></v-btn>
                        <v-btn @click="closeAll" text="Zamknij"></v-btn>
                    </div>
                </div>
            </v-card>
        </template>
    </v-dialog>
</template>

