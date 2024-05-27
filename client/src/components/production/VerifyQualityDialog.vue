<script setup lang="ts">
import { isReactive, onMounted, ref, Ref } from 'vue';
import { useOrdersStore } from '../../stores/ordersStore';
import { Order } from '../../models/order';

const props = defineProps<{
    isActive: Ref<boolean>
}>();

const isLoading = ref<boolean>(false);
const correctionsLoading = ref<boolean>(false);
const isSuccesfullyAdded = ref<boolean>(false);
const needsCorrections = ref<boolean>(false);

const orders = ref();
const orderStore = useOrdersStore();

onMounted(async () => {
    orders.value = await orderStore.dispatchGetOrders({ stateId: 2, page: 1, size: 10 })
})

const startProduction = () => {
    isLoading.value = true;
    setTimeout(() => {
        isLoading.value = false;
        isSuccesfullyAdded.value = true;
    }, 2000);
}
const markAsToCorrect = () => {
    correctionsLoading.value = true;
    setTimeout(() => {
        correctionsLoading.value = false;
        needsCorrections.value = true;
    }, 2000);
}

const closeAll = () => {
    isSuccesfullyAdded.value = false;
    props.isActive.value = false;
}

function itemProps(item: any) {
    return {
        title: `Zamówienie ${item.id}`,
        subtitle: `Zamówione przez ${item.client.name} dnia ${item.date}`,
    }
}
</script>

<template>
    <v-card class="pa-4">
        <h4 class="text-h6 mb-4 side-line">Weryfikacja jakości</h4>

        <v-select :item-props="itemProps" label="Wybierz zamówienie do weryfikacji" variant="outlined" :items="orders">
            <template v-slot:item="{ props, item }">
                <v-list-item v-bind="props">
                    <template v-slot:append>
                        <v-tooltip text="Pobierz dokument pdf">
                            <template v-slot:activator="{ props }">
                                <v-btn v-bind="props" rounded="lg" icon="mdi-file" color="primary" variant="text" size="small"></v-btn>
                            </template>
                        </v-tooltip>
                    </template>
                </v-list-item>
            </template>
        </v-select>
        <p>Oznacz maszyne jako sprawdzoną technicznie</p>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="!isSuccesfullyAdded" :loading="correctionsLoading" text="Wymaga poprawek"
                @click="markAsToCorrect"></v-btn>
            <v-btn v-if="!isSuccesfullyAdded" :loading="isLoading" variant="flat" color="primary"
                text="Pomyślna weryfikacja" @click="startProduction"></v-btn>

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

