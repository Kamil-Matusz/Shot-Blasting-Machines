<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import type { VDataTable } from 'vuetify/components'
import { reactive, ref } from 'vue';
import ModelForm from '@/components/models/ModelForm.vue';
import { useToast } from "vue-toastification";
import { InputCreateModel } from '@/models/model';
import { useModelStore } from '@/stores/modelStore';

type ReadonlyHeaders = VDataTable['$props']['headers']

const modelStore = useModelStore();
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
  { title: 'Uwagi', key: 'comments', align: 'start'},
  { title: 'Akcje', key: 'actions', align: 'end' },
]

//Temporary items to replace with data from store
const items = ref([{
  id: 1,
  name: "Standard",
  price: 50000.00,
  comments: "Podstawowy model śrutownicy"
},
{
  id: 2,
  name: "XL",
  price: 10000.00,
  comments: "Model w wersji XL z powiększonym koszem"
},
{
  id: 3,
  name: "XXL",
  price: 10000.00,
  comments: "Model w wersji XXL z maksymalnym koszem"
},]);

//Temporary options to replace with real pagination data
const options = ref({
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
})

const modelToAdd = ref(new InputCreateModel());

const add = () => {
  // materialsStore.dispatchCreateMaterial(materialToAdd) <= when api will be working;
  toast.success("Pomyślnie dodano nowy model", {
    timeout: 2000
  });
  modelToAdd.value = new InputCreateModel();
}

const deleteModel = (id: number) => {
  // materialsStore.dispatchDeleteMaterial(id) <= when api will be working;
  toast.success("Pomyślnie usunięto model", {
    timeout: 2000
  });
}

</script>

<template>
  <BasePage title="Konstrukcja maszyn">
    <v-dialog max-width="500">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn v-bind="activatorProps" color="surface-variant" variant="flat">+Dodaj nowy model</v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card title="Nowy model" rounded="lg">
          <ModelForm v-model="modelToAdd" @on-valid-submit="add(), isActive.value = false"></ModelForm>
        </v-card>
      </template>
    </v-dialog>
    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="items"
      :items-length="options.totalItems" :loading="options.loading" item-value="name">
      <template v-slot:item.actions="{ item }" dense>
        <v-btn @click="deleteModel(item.id)" rounded="lg" size="small" color="red" class="ml-2"
          icon="mdi-delete"></v-btn>
      </template>
    </v-data-table-server>
  </BasePage>
</template>
