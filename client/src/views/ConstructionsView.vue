<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { ref, computed } from 'vue'

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

const selectedMaterial = ref('');
const quantity = ref(1);
const selectedMaterials = ref([]);
const materials = ref([
  { name: 'Stal', price: 10 }, 
  { name: 'Farba', price: 5 }, 
  { name: 'Łożysko', price: 8 }, 
]);

function calculatePrice(material) {
  const selectedMaterial = materials.value.find(item => item.name === material);
  return selectedMaterial ? selectedMaterial.price : 0;
}

const headers = [
  { text: 'Materiał', align: 'start', value: 'material' },
  { text: 'Ilość', value: 'quantity' },
  { text: 'Akcja', value: 'action', sortable: false },
];

function addMaterial() {
  if (selectedMaterial.value && quantity.value > 0) {
    const existingMaterialIndex = selectedMaterials.value.findIndex(item => item.material === selectedMaterial.value);

    if (existingMaterialIndex !== -1) {
      selectedMaterials.value[existingMaterialIndex].quantity += quantity.value;
    } else {
      selectedMaterials.value.push({ material: selectedMaterial.value, quantity: quantity.value });
    }

    selectedMaterial.value = ''; 
    quantity.value = 1; 
  }
}

function removeMaterial(item) {
  const index = selectedMaterials.value.indexOf(item);
  if (index !== -1) {
    selectedMaterials.value.splice(index, 1);
  }
}

const totalPrice = computed(() => {
  let total = 0;
  for (const item of selectedMaterials.value) {
    const materialPrice = calculatePrice(item.material);
    total += materialPrice * item.quantity;
  }
  return total;
});
</script>

<template>
  <BasePage title="Konstrukcja maszyn">
    <v-sheet class="mx-auto" width="auto">
      <v-form @submit.prevent>
        <v-text-field label="Nazwa modelu"></v-text-field>

      <v-row>
        <v-col cols="12" md="6">
          <v-select v-model="selectedMaterial" :items="materials.map(material => material.name)" label="Wybierz materiał"></v-select>

        </v-col>
        <v-col cols="12" md="3">
          <v-text-field v-model.number="quantity" label="Ilość" type="number" min="1"></v-text-field>
        </v-col>
        <v-col cols="12" md="3">
          <v-btn color="primary" @click="addMaterial" :disabled="!selectedMaterial || quantity < 1">Dodaj materiał</v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12">
          <v-data-table :headers="headers" :items="selectedMaterials" hide-default-footer>
            <template v-slot:item.action="{ item }">
              <v-btn color="error" @click="removeMaterial(item)">Usuń</v-btn>
            </template>
          </v-data-table>
        </v-col>
      </v-row>

        <p class="font-weight-bold mb-5">
          Załączniki
        </p>

        <v-file-input label="Zdjęcia produktu" counter multiple show-size></v-file-input>

        <v-textarea label="Uwagi"></v-textarea>

        <v-card class="mb-5 mt-5" title="Koszt" subtitle="Poniżej znajduje się bazowy koszt urządzenia."
          variant="outlined">
          <h1 class="mb-4 text-center">
            {{ totalPrice }} PLN
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
