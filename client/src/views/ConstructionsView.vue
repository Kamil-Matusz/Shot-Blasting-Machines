<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { ref } from 'vue'

const tickLabels = ref({
  0: 'Mały',
  1: 'Średni',
  2: 'Duży',
  3: 'Ekstra duży',
})

const isLoading = ref(false);
const isActive = ref(false);
const isSaved = ref(false);

function confirm() {
  isLoading.value = true;

  setTimeout(() => {
    isLoading.value = false;  
    isSaved.value = true;
  }, 2000);

}
</script>

<template>
  <BasePage title="Konstrukcja maszyn">
    <v-sheet class="mx-auto" width="auto">
      <v-form @submit.prevent>
        <v-text-field label="Nazwa modelu"></v-text-field>

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
        <v-slider :max="3" :ticks=tickLabels show-ticks="always" step="1" tick-size="4" class="mb-5"></v-slider>

        <p class="font-weight-bold mb-5">
          Załączniki
        </p>
        <v-file-input label="Dokumentacja" counter multiple show-size></v-file-input>

        <v-file-input label="Zdjęcia produktu" counter multiple show-size></v-file-input>

        <v-textarea label="Uwagi"></v-textarea>

        <v-card class="mb-5 mt-5" title="Koszt" subtitle="Poniżej znajduje się bazowy koszt urządzenia."
          variant="outlined">
          <h1 class="mb-4 text-center">
            0 PLN
          </h1>
        </v-card>

        <v-dialog max-width="500" v-model="isActive">
          <template v-slot:activator="{ props: activatorProps }">
            <v-row class="align-center justify-end">
              <v-btn color="blue" class="ma-3" v-bind="activatorProps" text="Dodaj maszynę" variant="flat"></v-btn>
            </v-row>
          </template>

          <template v-slot:default>
            <template v-if="isLoading">
              <v-card>
                <v-card-text class="ma-5">
                  <v-row class="align-center justify-space-between">
                    Zapisuję maszynę
                    <v-progress-circular indeterminate color="blue"></v-progress-circular>
                  </v-row>
                </v-card-text>
              </v-card>
            </template>
            <template v-else-if="isSaved">
              <v-card title="Sukces">
                <v-card-text class="ma-5">
                  Twoja maszyna została poprawnie zapisana
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn text="Powrót" @click="isActive = false; isSaved = false"></v-btn>
                </v-card-actions>
              </v-card>
            </template>
            <template v-else>
              <v-card title="Potwierdź">
                <v-card-text>
                  Czy na pewno chcesz dodać nową maszynę?
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue" class="ma-3" text="Dodaj maszynę" variant="flat" @click="confirm"></v-btn>
                  <v-btn text="Anuluj" @click="isActive = false"></v-btn>
                </v-card-actions>
              </v-card>
            </template>

          </template>
        </v-dialog>
      </v-form>
    </v-sheet>
  </BasePage>
</template>
