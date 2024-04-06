<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { ref } from 'vue'

const tickLabels = ref({
  0: 'Mały',
  1: 'Średni',
  2: 'Duży',
  3: 'Ekstra duży',
})
</script>

<template>
  <BasePage title="Konstrukcja maszyn">
    <v-sheet class="mx-auto" width="auto">
    <v-form @submit.prevent>
      <v-text-field
        label="Nazwa modelu"
      ></v-text-field>

      <p class="font-weight-bold">
      Typ kosza
      </p>
      <v-radio-group inline>
        <v-radio label="brak" value="no"></v-radio>
        <v-radio label="lekki" value="light"></v-radio>
        <v-radio label="ciężki" value="heavy"></v-radio>
      </v-radio-group>

      <p class="font-weight-bold">
      Sterowanie
      </p>
      <v-radio-group inline>
        <v-radio label="manualne" value="no"></v-radio>
        <v-radio label="ekran + przyciski" value="light"></v-radio>
        <v-radio label="ekran dotykowy" value="heavy"></v-radio>
      </v-radio-group>

      <p class="font-weight-bold mb-2">
      Silnik
      </p>
      <v-autocomplete label="Wybierz model" :items="['11kW ytong', '7kW tamel']">
      </v-autocomplete>
      <v-checkbox label="Regulacja obrotów"></v-checkbox>

      <p class="font-weight-bold">
      Pozostałe opcje
      </p>
      <v-checkbox label="Zawieszka"></v-checkbox>

      <p class="font-weight-bold mb-2">
      Rozmiar komory
      </p>    
      <v-slider
      :max="3"
      :ticks=tickLabels
      show-ticks="always"
      step="1"
      tick-size="4"
      class="mb-5"
      ></v-slider>

      <p class="font-weight-bold mb-5">
      Załączniki
      </p>    
      <v-file-input
        label="Dokumentacja"
        counter
        multiple
        show-size
      ></v-file-input>

      <v-file-input
        label="Zdjęcia produktu"
        counter
        multiple
        show-size
      ></v-file-input>

      <v-textarea label="Uwagi"></v-textarea>


      <v-btn class="mt-2" block type="submit">Dodaj maszynę</v-btn>
    </v-form>
  </v-sheet>
  </BasePage>
</template>
