<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import BaseCardWithHover from '@/components/cards/BaseCardWithHover.vue';
import StartProductionDialog from '@/components/production/StartProductionDialog.vue';
import VerifyQualityDialog from '@/components/production/VerifyQualityDialog.vue';
import SendToClientDialog from '@/components/production/SendToClientDialog.vue';
</script>

<template>
  <BasePage title="Produkcja">

    <div class="d-flex flex-wrap w-100 h-100" style="gap: 1rem;">
      <div class="min-w-30" style="flex-grow: 3;">
        <v-dialog max-width="500">
          <template v-slot:activator="{ props: activatorProps }">
            <div v-bind="activatorProps">
              <BaseCardWithHover overflow-hidden cursor="pointer" style="height: 20rem;">
                <div class="d-flex justify-center align-center h-100 flex-column cursor-pointer scale-hover">
                  <div><v-icon size="120">mdi-check</v-icon></div>
                  <p class="text-h5">Przyjmij zamówienie do produkcji</p>
                </div>
              </BaseCardWithHover>
            </div>
          </template>

          <template v-slot:default="{ isActive }">
            <StartProductionDialog :is-active="isActive">
            </StartProductionDialog>

          </template>
        </v-dialog>

      </div>
      <div class="min-w-30" style="flex-grow: 3;">
        <v-dialog max-width="500">
          <template v-slot:activator="{ props: activatorProps }">
            <div v-bind="activatorProps">
              <BaseCardWithHover overflow-hidden style="height: 20rem;overflow:hidden ">
                <div class="d-flex justify-center align-center h-100 flex-column cursor-pointer scale-hover">
                  <div><v-icon size="120">mdi-playlist-check</v-icon></div>
                  <p class="text-h5">Weryfikacja jakości</p>
                </div>
              </BaseCardWithHover>
            </div>
          </template>

          <template v-slot:default="{ isActive }">
            <VerifyQualityDialog :is-active="isActive">
            </VerifyQualityDialog>

          </template>
        </v-dialog>
      </div>
      <div class="min-w-30" style="flex-grow: 3;">

        <v-dialog max-width="500">
          <template v-slot:activator="{ props: activatorProps }">
            <div v-bind="activatorProps">
              <BaseCardWithHover overflow-hidden style="height: 20rem;">
                <div class="d-flex justify-center align-center h-100 flex-column cursor-pointer scale-hover">
                  <div><v-icon size="120">mdi-truck-delivery</v-icon></div>
                  <p class="text-h5">Wyślij zamówienie do klienta</p>
                </div>
              </BaseCardWithHover>
            </div>
          </template>
          <template v-slot:default="{ isActive }">
            <SendToClientDialog :is-active="isActive">
            </SendToClientDialog>
          </template>
        </v-dialog>
      </div>
    </div>
  </BasePage>
</template>

