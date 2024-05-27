<script setup lang="ts">
import { isReactive, ref, Ref, onMounted } from 'vue';
import { useOrdersStore } from '../../stores/ordersStore';

const props = defineProps<{
    isActive: Ref<boolean>
}>();

const isLoading = ref<boolean>(false);
const isSuccesfullyAdded = ref<boolean>(false);
const orders = ref();
const orderStore = useOrdersStore();

onMounted(async () => {

    orders.value = await orderStore.dispatchGetOrders({ stateId: 1, page: 1, size: 10 })
})


const startProduction = () => {
    isLoading.value = true;
    setTimeout(() => {
        isLoading.value = false;
        isSuccesfullyAdded.value = true;
    }, 2000);
}

const closeAll = () => {
    isSuccesfullyAdded.value = false;
    props.isActive.value = false;
}
</script>

<template>
    <v-card class="pa-4">
        <h4 class="text-h6 mb-4 side-line">Rozpoczęcie produkcji </h4>

        <v-select label="Wybierz zamówienie" variant="outlined" :items="orders"></v-select>
        <p>Rozpoczęcie produkcji zmieni status zamoowienia oraz zaksięguje pobranie materiałów z magazynu </p>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="!isSuccesfullyAdded" :loading="isLoading" variant="flat" color="primary"
                text="Rozpocznij produkcje" @click="startProduction"></v-btn>

        </v-card-actions>
    </v-card>
    <v-dialog max-width="500" v-model="isSuccesfullyAdded">
        <template v-slot:default="{ isActive }">
            <v-card class="pa-4" rounded="lg">
                <div class="d-flex flex-column justify-center align-center">
                    <v-icon size="80" color="primary">mdi-check</v-icon>
                    <p class="py-4 text-center"> Pomyślnie rozpoczęto produkcje zamówienia i pobrano potrzebne materiały z
                        magazynu</p>
                    <div class="d-flex">
                        <v-btn variant="flat" color="primary" text="Zobacz szczegóły zamówienia"></v-btn>
                        <v-btn @click="closeAll" text="Zamknij"></v-btn>
                    </div>
                </div>
            </v-card>
        </template>
    </v-dialog>
</template>

