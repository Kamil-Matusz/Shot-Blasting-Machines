<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import BaseCardWithHover from '@/components/cards/BaseCardWithHover.vue';
import { ref, onMounted } from 'vue';
import OrdersView from './OrdersView.vue';
import OrdersTable from '@/components/orders/OrdersTable.vue';
import Vue3Odometer from 'vue3-odometer'
import 'odometer/themes/odometer-theme-default.css'
import { useStatsStore } from '../stores/statsStore';

const statsStore = useStatsStore();



onMounted(async () => {
  await statsStore.dispatchGetStats();
  await statsStore.dispatchGetSparkLine();
})

const gradient = ref(['orange', 'yellow'])

</script>

<template>
  <BasePage title="Dashboard">
    <div>
      <v-row>
        <v-col cols="4">
          <BaseCardWithHover title="Przychód netto">
            <span class="text-h2">
              <Vue3Odometer :value="statsStore.stats?.totalGross" format="(,ddd).dd"></Vue3Odometer> <span
                class="text-h5">PLN</span>
                <div class="-mt-16 sparkline" style="margin-top: -2rem;">
                  <v-sparkline :gradient="gradient" line-width="2" smooth v-model="statsStore.sparkline"></v-sparkline>
                </div>
            </span>
          </BaseCardWithHover>
        </v-col>
        <v-col cols="4">
          <BaseCardWithHover title="Ilość zamówień">
            <Vue3Odometer class="text-h2" :value="statsStore.stats?.totalAmountOfOrders"></Vue3Odometer>
          </BaseCardWithHover>
        </v-col>
        <v-col cols="4">

          <BaseCardWithHover title="Nowi klienci">
            <span class="text-h2">
              <Vue3Odometer :value="statsStore.stats?.totalAmountOfClients"></Vue3Odometer>
            </span>
          </BaseCardWithHover>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <BaseCardWithHover title="Nowe zamówienia">

            <OrdersTable :state-id="1"></OrdersTable>
          </BaseCardWithHover>
        </v-col>
      </v-row>
    </div>


  </BasePage>
</template>


<style lang="scss">
.sparkline {
  animation: draw 2s;
  height: 10rem;
}

@keyframes draw {
  0% {
    transform: scaleY(0);
  }

  100% {
    transform: scaleY(1);
  }
}
</style>
