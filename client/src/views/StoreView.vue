<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useMaterialsStore } from '../stores/materialStore';
import type { VDataTable } from 'vuetify/components'
import { reactive, ref, onMounted } from 'vue';
import { InputCreateMaterial } from '../models/material';
import MaterialForm from '@/components/materials/MaterialForm.vue';
import { useToast } from "vue-toastification";

type ReadonlyHeaders = VDataTable['$props']['headers']

const materialsStore = useMaterialsStore();
const toast = useToast();

const headers: ReadonlyHeaders = [
  {
    title: 'Id',
    align: 'start',
    sortable: false,
    key: 'id',
  },
  { title: 'Nazwa', key: 'name', align: 'start' },
  { title: 'Cena', key: 'price', align: 'end' },
  { title: 'Ilość', key: 'amount', align: 'center', width: "200px" },
  { title: 'Akcje', key: 'actions', align: 'end', width: "50px" },
]

//Temporary options to replace with real pagination 
const options = ref({
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
})

const materialToAdd = ref(new InputCreateMaterial());
const amountToAdd = ref(0);
const amountToTake = ref(0);

const add = async () => {
  await materialsStore.dispatchCreateMaterial(materialToAdd.value);
  toast.success("Pomyślnie dodano nowy materiał");
  materialToAdd.value = new InputCreateMaterial();
}

const addMaterialAmount = async (id: number) => {
  await materialsStore.dispatchAddMaterialAmount(id, amountToAdd.value);
  toast.success("Pomyślnie dodano nowe sztuki materiału");
  amountToAdd.value = 0;
}

const takeMaterialAmount = async (id: number) => {
  await materialsStore.dispatchTakeMaterial(id, amountToTake.value);
  toast.success("Pomyślnie zabrano sztuki sztuki materiału z magazynu");
  amountToTake.value = 0;
}

const deleteMaterial = async (id: number) => {
  await materialsStore.dispatchDeleteMaterial(id);
  toast.success("Pomyślnie usunięto materiał")
}

onMounted(async () => {
  await materialsStore.dispatchGetMaterials();
})

</script>

<template>
  <BasePage title="Magazyn">
    <template #above-card>
      <v-dialog max-width="500">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn v-bind="activatorProps" color="primary" variant="flat" style="width: 20rem" class="mb-4">+Dodaj nowy
            materiał</v-btn>
        </template>

        <template v-slot:default="{ isActive }">
          <v-card title="Nowy materiał" rounded="lg">
            <MaterialForm v-model="materialToAdd" @on-valid-submit="add(), isActive.value = false"></MaterialForm>
          </v-card>
        </template>
      </v-dialog>
    </template>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers"
      :items="materialsStore.materials" :items-length="options.totalItems" :loading="options.loading" item-value="name">
      <template v-slot:item.amount="{ item }" dense>
        <div class="d-flex justify-end align-center" style="max-width:10rem">
          <v-dialog max-width="500">
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn v-bind="activatorProps" rounded="lg" size="small" variant="flat" icon="mdi-minus"
                color="primary"></v-btn>
            </template>
            <template v-slot:default="{ isActive }">
              <v-card title="Ilość do odjęcia">
                <v-form class="pa-4" @submit.prevent="takeMaterialAmount(item.id)">
                  <v-text-field v-model.number="amountToTake" density="compact" variant="outlined"
                    label="Ilość"></v-text-field>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn type="submit" text="Zatwierdź" color="surface-variant" variant="flat"
                      @click="isActive.value = false"></v-btn>
                  </v-card-actions>
                </v-form>
              </v-card>
            </template>
          </v-dialog>
          <div class="font-weight-medium text-center" style="width:5rem">
            {{ item.amount }}
          </div>
          <v-dialog max-width="500">
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn v-bind="activatorProps" rounded="lg" size="small" variant="flat" color="primary"
                icon="mdi-plus"></v-btn>
            </template>
            <template v-slot:default="{ isActive }">
              <v-card title="Ilość do dodania">
                <v-form class="pa-4" @submit.prevent="addMaterialAmount(item.id)">
                  <v-text-field v-model.number="amountToAdd" density="compact" variant="outlined"
                    label="Ilość"></v-text-field>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn type="submit" text="Dodaj" color="primary" variant="flat"
                      @click="isActive.value = false"></v-btn>
                  </v-card-actions>
                </v-form>
              </v-card>
            </template>
          </v-dialog>
        </div>

      </template>
      <template v-slot:item.price="{ item }" dense>
        {{ item.price }} PLN
      </template>
      <template v-slot:item.actions="{ item }" dense>
        <v-btn @click="deleteMaterial(item.id)" rounded="lg" size="small" color="red" class="ml-2"
          icon="mdi-delete"></v-btn>
      </template>
    </v-data-table-server>
  </BasePage>
</template>
