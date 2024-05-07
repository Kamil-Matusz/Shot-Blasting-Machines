<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import BaseCardWithHover from '@/components/cards/BaseCardWithHover.vue';
</script>

<template>
  <BasePage title="Produkcja">
    
    <v-row style="height: 20rem;">
      <v-col cols="4">
        <BaseCardWithHover style="height: 20rem;">
          <div class="d-flex justify-center align-center h-100 flex-column">
            <div><v-icon size="120">mdi-check</v-icon></div>
            <p class="text-h5">Przyjmij zamówienie do produkcji</p>
          </div>
        </BaseCardWithHover>
      </v-col>
      <v-col cols="4">
        <BaseCardWithHover style="height: 20rem;">
          <div class="d-flex justify-center align-center h-100 flex-column">
            <div><v-icon size="120">mdi-playlist-check</v-icon></div>
            <p class="text-h5">Weryfikacja jakości</p>
          </div>
        </BaseCardWithHover>
      </v-col>
      <v-col cols="4">
        <BaseCardWithHover style="height: 20rem;">
          <div class="d-flex justify-center align-center h-100 flex-column">
            <div><v-icon size="120">mdi-truck-delivery</v-icon></div>
            <p class="text-h5">Wyślij zamówienie do klienta</p>
          </div>
        </BaseCardWithHover>
      </v-col>
    </v-row>
  </BasePage>
</template>
