<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import type { VDataTable } from 'vuetify/components'
import { reactive, ref } from 'vue';
import { InputCreateMaterial, type Material } from '@/models/material';
import MaterialForm from '@/components/materials/MaterialForm.vue';
import { useToast } from "vue-toastification";
import SelectMaterialForm from '../materials/SelectMaterialForm.vue';

type ReadonlyHeaders = VDataTable['$props']['headers']

const toast = useToast();

const headers: ReadonlyHeaders = [
  { title: 'Nazwa', key: 'material.name', align: 'start' },
  { title: 'Ilość', key: 'amount', align: 'start', width: "200px" },
  { title: 'Akcje', key: 'actions', align: 'end' },
]



//Temporary options to replace with real pagination data
const options = ref({
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
})

const materialToAdd = ref(new InputCreateMaterial());
const amountToAdd = ref(0);

const add = () => {
  // materialsStore.dispatchCreateMaterial(materialToAdd) <= when api will be working;
  toast.success("Pomyślnie dodano nowy materiał", {
    timeout: 2000
  });
  materialToAdd.value = new InputCreateMaterial();
}

const addMaterialAmount = (id: number) => {
  //materialsStore.dispatchAddMaterialAmount(id,amountToAdd.value) <= when api will be working;
  toast.success("Pomyślnie dodano nowe sztuki materiału", {
    timeout: 2000
  });
  amountToAdd.value = 0;
}

const deleteMaterial = (id: number) => {
  // materialsStore.dispatchDeleteMaterial(id) <= when api will be working;
  toast.success("Pomyślnie usunięto materiał", {
    timeout: 2000
  });
}

// Define the props
const props = defineProps<{
  items: Material[]; // Define the type as per your data structure
}>();
</script>

<template>
    <v-card width="100%">
    <v-dialog>
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn v-bind="activatorProps" color="surface-variant" variant="flat">+Dodaj materiał</v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card title="Dodaj materiał" rounded="lg">
          <SelectMaterialForm  v-model="materialToAdd" @on-valid-submit="add(), isActive.value = false"></SelectMaterialForm>
        </v-card>
      </template>
    </v-dialog>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="props.items"
      :items-length="options.totalItems" :loading="options.loading" item-value="name">
      <template v-slot:item.amount="{ item }" dense>
        <div class="d-flex justify-space-between" style="max-width:7rem">
          {{ item.amount }}
          <v-dialog>
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn v-bind="activatorProps" rounded="lg" size="small" variant="flat"
                color="surface-variant">Dodaj</v-btn>
            </template>

            <template v-slot:default="{ isActive }">
              <v-card title="Ilość do dodania">
                <v-form class="pa-4" @submit.prevent="addMaterialAmount(item.id)">
                  <v-text-field v-model.number="amountToAdd" density="compact" variant="outlined"
                    label="Ilość"></v-text-field>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn type="submit" text="Dodaj" color="surface-variant" variant="flat"
                      @click="isActive.value = false"></v-btn>
                  </v-card-actions>
                </v-form>
              </v-card>
            </template>
          </v-dialog>
        </div>

      </template>
      <template v-slot:item.actions="{ item }" dense>
        <v-btn @click="deleteMaterial(item.id)" rounded="lg" size="small" color="red" class="ml-2"
          icon="mdi-delete"></v-btn>
      </template>
    </v-data-table-server>
</v-card>
</template>
