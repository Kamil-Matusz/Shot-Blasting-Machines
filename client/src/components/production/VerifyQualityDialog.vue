<script setup lang="ts">
import { isReactive, onMounted, ref, Ref } from 'vue';
import { useOrdersStore } from '../../stores/ordersStore';
import { Order } from '../../models/order';
import { useToast } from 'vue-toastification';
import OrderSelect from '../orders/OrderSelect.vue';
import OrderState from '@/models/orderState';

const props = defineProps<{
    isActive: Ref<boolean>
}>();

const toast = useToast();

const isLoading = ref<boolean>(false);
const correctionsLoading = ref<boolean>(false);
const isSuccesfullyAdded = ref<boolean>(false);
const needsCorrections = ref<boolean>(false);

const orders = ref();
const orderStore = useOrdersStore();

const handleOrderAction =  async(action: (id: number) => Promise<void>) => {
    isLoading.value = true;
    if (selectedOrder.value?.id) {
        await action(selectedOrder.value.id);
    } else {
        toast.error("Wybierz zamówienie");
    }
    isLoading.value = false;
    isSuccesfullyAdded.value = true;
}

const confirmQuality = () => handleOrderAction(orderStore.dispatchQualityConfirm);
const markAsToCorrect = () => handleOrderAction(orderStore.dispatchQualityDecline);


const closeAll = () => {
    isSuccesfullyAdded.value = false;
    props.isActive.value = false;
}

const selectedOrder = ref<Order>();
</script>

<template>
    <v-card class="pa-4">
        <h4 class="text-h6 mb-4 side-line">Weryfikacja jakości</h4>
        <order-select v-model="selectedOrder" :state="OrderState.TO_BE_CHECKED"></order-select>
        <p>Oznacz maszyne jako sprawdzoną technicznie</p>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="!isSuccesfullyAdded" :disabled="selectedOrder === undefined" :loading="correctionsLoading" text="Wymaga poprawek"
                @click="markAsToCorrect"></v-btn>
            <v-btn v-if="!isSuccesfullyAdded" :disabled="selectedOrder === undefined" :loading="isLoading" variant="flat" color="primary"
                text="Pomyślna weryfikacja" @click="confirmQuality"></v-btn>

        </v-card-actions>
    </v-card>
    <v-dialog max-width="500" v-model="isSuccesfullyAdded">
        <template v-slot:default="{ isActive }">
            <v-card class="pa-4" rounded="lg">
                <div class="d-flex flex-column justify-center align-center">
                    <v-icon size="80" color="primary">mdi-check</v-icon>
                    <p class="py-4 text-center">Pomyślnie zmieniono status zamówienia</p>
                    <div class="d-flex">
                        <v-btn variant="flat" color="primary" text="Zobacz szczegóły zamówienia"></v-btn>
                        <v-btn @click="closeAll" text="Zamknij"></v-btn>
                    </div>
                </div>
            </v-card>
        </template>
    </v-dialog>
    <v-dialog max-width="500" v-model="needsCorrections">
        <template v-slot:default="{ isActive }">
            <v-card class="pa-4" rounded="lg">
                <div class="d-flex flex-column justify-center align-center">
                    <v-icon size="80" color="primary">mdi-check</v-icon>
                    <p class="py-4 text-center">Oznaczono zamówienie jako wymagające poprawek</p>
                    <div class="d-flex">
                        <v-btn variant="flat" color="primary" text="Zobacz szczegóły zamówienia"></v-btn>
                        <v-btn @click="closeAll" text="Zamknij"></v-btn>
                    </div>
                </div>
            </v-card>
        </template>
    </v-dialog>
</template>

