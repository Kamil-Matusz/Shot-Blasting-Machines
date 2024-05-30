<script setup lang="ts">
import { isReactive, ref, Ref, onMounted } from 'vue';
import { useOrdersStore } from '../../stores/ordersStore';
import { Order } from '../../models/order';
import { useToast } from 'vue-toastification';
import OrderPdfPreview from '@/components/orders/OrderPdfPreview.vue';
import OrderSelect from '../orders/OrderSelect.vue';
import OrderState from '@/models/orderState';

const props = defineProps<{
    isActive: Ref<boolean>
}>();

const isLoading = ref<boolean>(false);
const isSuccesfullyAdded = ref<boolean>(false);
const orderStore = useOrdersStore();
const toast = useToast();


const markAsToCheck = async () => {
    isLoading.value = true;
    if (selectedOrder.value?.id)
        await orderStore.dispatchMarkAsToCheck(selectedOrder.value?.id);
    else
        toast.error("Wybierz zamówienie")
    isLoading.value = false;
    isSuccesfullyAdded.value = true;
}

const closeAll = () => {
    isSuccesfullyAdded.value = false;
    props.isActive.value = false;
}


const selectedOrder = ref<Order>();
</script>

<template>
    <v-card class="pa-4">
        <h4 class="text-h6 mb-4 side-line">Gotowość do sprawdzanie</h4>
        <OrderSelect v-model="selectedOrder" :state="OrderState.IN_PRODUCTION"></OrderSelect>
        <p>Jeżeli maszyna z zamówienia została wykonana oznacz ją jako gotową do sprawdzenia </p>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="!isSuccesfullyAdded" :disabled="selectedOrder === undefined" :loading="isLoading" variant="flat"
                color="primary" text="Zatwierdź" @click="markAsToCheck"></v-btn>

        </v-card-actions>
    </v-card>
    <v-dialog max-width="500" v-model="isSuccesfullyAdded">
        <template v-slot:default="{ isActive }">
            <v-card class="pa-4" rounded="lg">
                <div class="d-flex flex-column justify-center align-center">
                    <v-icon size="80" color="primary">mdi-check</v-icon>
                    <p class="py-4 text-center"> Pomyślnie oznaczono maszyne jako gotową do sprawdzenia</p>
                    <div class="d-flex">
                        <v-btn v-if="selectedOrder" variant="flat" color="primary" :to="`/orders/${selectedOrder?.id}`" text="Zobacz szczegóły zamówienia"></v-btn>
                        <v-btn @click="closeAll" text="Zamknij"></v-btn>
                    </div>
                </div>
            </v-card>
        </template>
    </v-dialog>
</template>

