<script setup lang="ts">
import { useOrdersStore } from '@/stores/ordersStore';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import BasePage from '@/components/pages/BasePage.vue';
import BaseCardWithHover from '@/components/cards/BaseCardWithHover.vue';
import { Order } from '../models/order';

const ordersStore = useOrdersStore();
const route = useRoute();
const order = ref<Order>();

onMounted(async () => {
    const id = Number(route.params.id);
    if (id) {
        order.value = await ordersStore.dispatchGetOrderByID(id);
    }
    if (order.value)
        order.value.machine.accessories = [{ id: 1, name: "Test", price: 120 }, { id: 2, name: "Test2", price: 120 }]
});
</script>

<template>
    <BasePage v-if="order" :title="`Zamówienie nr ${order.id}`">
        <v-btn class="bottom-right" color="primary" icon="mdi-file"></v-btn>
        <div class="text-h6 top-right">Cena netto: {{ order.price.toFixed(2) }} PLN</div>
        <div class="mb-4 d-flex justify-space-between">
            <v-chip color="primary" size="x-large">{{ order.state }}</v-chip>

        </div>
        <div class="mb-4">
            Data złożenia zamówienia: {{ order.date }}
        </div>
        <div class="d-flex gap-1">
            <BaseCardWithHover class="min-w-20 min-h-10" style="flex-grow: 3;">
                <h6 class="text-h6 side-line mb-4" color="primary">
                    Klient:
                </h6>
                <p>{{ order.client.name }}</p>
                <p>Adres: {{ order.client.address }}</p>
                <p>Numer telefonu:{{ order.client.phoneNumber }}</p>
            </BaseCardWithHover>
            <BaseCardWithHover class="min-w-20 min-h-10" style="flex-grow: 3;">
                <h6 class="text-h6 side-line mb-4" color="primary">
                    Utworzył zamówienie:
                </h6>
                <p>{{ order.user.role.name }} {{ order.user.name }}</p>
                <p>Adres: {{ order.user.email }}</p>
                <p>Numer telefonu:{{ order.client.phoneNumber }}</p>
            </BaseCardWithHover>
            <BaseCardWithHover class="min-w-20 min-h-10" style="flex-grow: 3;">
                <v-btn class="top-right" color="primary" variant="text" icon="mdi-pencil"></v-btn>
                <h6 class="text-h6 side-line mb-4" color="primary">
                    Komentarze:
                </h6>
                <p>{{ order.comments }}</p>
            </BaseCardWithHover>
        </div>
        <div class="d-flex gap-1 mt-4">
            <BaseCardWithHover class="min-w-20 min-h-10" style="flex-grow: 3;">
                <h6 class="text-h5 mb-4 " color="primary">
                    Maszyna {{ order.machine.model.name }} nr : {{ order.machine.id }}
                </h6>
                <p>Akcesoria: <v-chip v-if="order.machine.accessories.length > 0"
                        v-for="accessory in order.machine.accessories" class="ml-2">{{ accessory.name }}</v-chip> <span
                        v-else>Brak</span></p>

            </BaseCardWithHover>
        </div>
    </BasePage>
</template>