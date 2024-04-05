<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
</script>

<template>
  <BasePage title="Dodaj zamówienie">
    <v-row>
      <v-col cols="12" lg="6">
        <v-card class="mb-5"
          variant="outlined"
          title="Śrutownica"
          subtitle="Wybierz z listy rowijanej maszynę, którą chce zamówić klient."
        >
          <v-select
            class="ml-4 mr-4"
            :items="['Śrutownica RB7', 'Śrutownica RB11']"
            label="Wybierz maszynę:"
          />
        </v-card>

        <v-card
          variant="outlined"
          title="Personalizacja"
          subtitle="Wybierz elementy, które życzy sobie klient."
        >
          <v-checkbox class="ml-3"
            hide-details
            label="Hak holowniczy"
            color="blue"
          />
          <v-checkbox class="ml-3"
            label="System odsysania pyłów"
            color="blue"
            hide-details
          />
          <v-checkbox class="ml-3"
            label="Dodatkowy zestaw kół"
            color="blue"
            hide-details
          />
        </v-card>

      </v-col>
      <v-col cols="12" lg="6">
        <v-card class="mb-5"
          title="Uwagi"
          subtitle="Zapytaj klienta o dodatkowe uwagi."
          variant="outlined"
        >
          <v-textarea class="ml-4 mr-4"
            clearable
          />
        </v-card>
        <v-card
          title="Podsumowanie"
          subtitle="Przedstaw klientowi wszystkie ustawiania i zapisz zamówienie."
          variant="outlined"
        >
          <p class="ml-4 mr-4">Jeżeli klient potwierdza złożenia zamówienia, zapisz je w bazie danych.</p>
          <v-btn color="blue" class="ma-3">Zapisz</v-btn>
        </v-card>
      </v-col>
    </v-row>
  </BasePage>
</template>
