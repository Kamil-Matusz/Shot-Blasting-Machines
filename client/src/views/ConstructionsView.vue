<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import type { VDataTable } from 'vuetify/components'
import { onMounted, ref } from 'vue';
import ModelForm from '@/components/models/ModelForm.vue';
import EditModelForm from '@/components/models/EditModelForm.vue';
import { useToast } from "vue-toastification";
import { InputEditModel, InputCreateModel, type Model } from '@/models/model';
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
  { title: 'Uwagi', key: 'comments', align: 'start' },
  { title: 'Akcje', key: 'actions', align: 'end' },
]

const items = ref<Model[]>([]);
console.log(items)

//Temporary options to replace with real pagination data
const options = ref({
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
})

const modelToAdd = ref(new InputCreateModel());
const addDialogVisible = ref(false); // State for Add Dialog
const editDialogVisible = ref(false); // State for Edit Dialog
const selectedModel = ref(new InputEditModel()); // Holds the selected model for editing
let selectedModelId = 0

const editModel = (item: Model) => {
  selectedModelId = item.id
  selectedModel.value = new InputEditModel(item);
  editDialogVisible.value = true; // Open the edit dialog
}

const add = () => {
  // materialsStore.dispatchCreateMaterial(materialToAdd) <= when api will be working;
  toast.success("Pomyślnie dodano nowy model", {
    timeout: 2000
  });
  modelToAdd.value = new InputCreateModel();
}

const deleteModel = async (id: number) => {
  try {
    await modelStore.dispatchDeleteModel(id);
    toast.success("Pomyślnie usunięto model", {
      timeout: 2000
    });
  } catch (error) {
    // Do nothing on error as it is handled by middleware
  }
};

const updateModel = async () => {
  try {
    await modelStore.dispatchUpdateModel(selectedModelId, selectedModel.value);
    toast.success("Pomyślnie zaktualizowano model", {
      timeout: 2000
    });
  } catch (error) {
      // Do nothing on error as it is handled by middleware
  }
};

onMounted(async () => {
  // Fetch models from the store
  await modelStore.dispatchGetModels();
  // Once data is fetched, assign it to the items ref
  items.value = modelStore.models;
});
</script>

<template>
  <BasePage title="Konstrukcja maszyn">

    <template #above-card>
      <!-- Add Model Dialog -->
      <v-dialog v-model="addDialogVisible" max-width="500">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn v-bind="activatorProps" color="primary" class="mb-4" style="max-width: 20rem;" variant="flat">+Dodaj nowy model</v-btn>
        </template>
        <v-card title="Dodaj nowy model" rounded="lg">
          <ModelForm :model-value="modelToAdd" @on-valid-submit="add(), addDialogVisible = false"></ModelForm>
        </v-card>
      </v-dialog>
    </template>

    <!-- Edit Model Dialog -->
    <v-dialog v-model="editDialogVisible" max-width="1000">
      <template v-slot:activator="{ props: activatorProps }">
        <!-- This activator is optional, you can place it where it fits your UI -->
      </template>
      <v-card title="Edytuj model" rounded="lg">
        <EditModelForm :items="selectedModel.neededMaterials" :model-value="selectedModel"
          @on-close="editDialogVisible = false" @updateModel="updateModel"></EditModelForm>
      </v-card>
    </v-dialog>

    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="items"
      :items-length="options.totalItems" :loading="options.loading" item-value="id">
      <template v-slot:item.actions="{ item }" dense>
        <v-btn @click="editModel(item)" rounded="lg" size="small" color="primary" class="ml-2" icon>
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn @click="deleteModel(item.id)" rounded="lg" size="small" color="red" class="ml-2" icon>
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </template>
    </v-data-table-server>
  </BasePage>
</template>
