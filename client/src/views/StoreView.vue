<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useMaterialsStore } from '../stores/materialStore';
import type { VDataTable } from 'vuetify/components'
import { reactive, ref } from 'vue';
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
  { title: 'Cena', key: 'price', align: 'start' },
  { title: 'Ilość', key: 'amount', align: 'start', width: "200px" },
  { title: 'Akcje', key: 'actions', align: 'end' },
]

//Temporary items to replace with data from store
const items = ref([{
  id: 1,
  name: "Blacha 100x500",
  price: 100.00,
  amount: 5
},
{
  id: 2,
  name: "Blacha 200x500",
  price: 200.00,
  amount: 0
},
{
  id: 3,
  name: "Blacha 300x500",
  price: 300.00,
  amount: 15
}, {
  id: 4,
  name: "Blacha 400x500",
  price: 400.00,
  amount: 3
}, {
  id: 5,
  name: "Blacha 500x500",
  price: 500.00,
  amount: 12
}]);

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

</script>

<template>
  <BasePage title="Magazyn">
    <v-dialog max-width="500">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn v-bind="activatorProps" color="surface-variant" variant="flat">+Dodaj nowy materiał</v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card title="Nowy materiał" rounded="lg">
          <MaterialForm v-model="materialToAdd" @on-valid-submit="add(), isActive.value = false"></MaterialForm>
        </v-card>
      </template>
    </v-dialog>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="items"
      :items-length="options.totalItems" :loading="options.loading" item-value="name">
      <template v-slot:item.amount="{ item }" dense>
        <div class="d-flex justify-space-between" style="max-width:7rem">
          {{ item.amount }}
          <v-dialog max-width="500">
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
  </BasePage>
</template>
