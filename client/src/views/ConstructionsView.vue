<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue';
import type { VDataTable } from 'vuetify/components';
import { onMounted, ref, watch } from 'vue';
import EditModelForm from '@/components/models/EditModelForm.vue';
import AddModelForm from '@/components/models/AddModelForm.vue';
import { useToast } from "vue-toastification";
import { InputEditModel, type Model } from '@/models/model';
import { useModelStore } from '@/stores/modelStore';
import { InputPagination, type PaginationParams } from '@/models/paginationParams';

type ReadonlyHeaders = VDataTable['$props']['headers'];

const modelStore = useModelStore();
const toast = useToast();

const pagination = ref(new InputPagination() as PaginationParams);

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
];

const items = ref<Model[]>([]);
console.log(items);

const options = ref({
  itemsPerPage: pagination.value.size || 10,
  loading: true,
  totalItems: 0,
});

const getModels = async () => {
  options.value.loading = true;
  await modelStore.dispatchGetModels(pagination.value);
  options.value.totalItems = modelStore.totalItems;
  items.value = modelStore.models;
  options.value.loading = false;
};

const modelToAdd = ref(new InputEditModel());
const addDialogVisible = ref(false); // State for Add Dialog
const editDialogVisible = ref(false); // State for Edit Dialog
const selectedModel = ref(new InputEditModel()); // Holds the selected model for editing
let selectedModelId = 0;

const editModel = (item: Model) => {
  selectedModelId = item.id;
  selectedModel.value = new InputEditModel(item);
  editDialogVisible.value = true; // Open the edit dialog
};

const add = async () => {
  try {
    modelToAdd.value.neededMaterials.forEach((neededMaterial) => {
      neededMaterial.id = neededMaterial.material.id;
    });
    console.log(modelToAdd);
    await modelStore.dispatchCreateModel(modelToAdd.value);
    toast.success("Pomyślnie dodano nowy model", {
      timeout: 2000,
    });
    modelToAdd.value = new InputEditModel();
    await getModels();
  } catch (error) {
    // Handle error
  }
};

const deleteModel = async (id: number) => {
  try {
    await modelStore.dispatchDeleteModel(id);
    toast.success("Pomyślnie usunięto model", {
      timeout: 2000,
    });
    await getModels();
  } catch (error) {
    // Handle error
  }
};

const updateModel = async () => {
  try {
    selectedModel.value.neededMaterials.forEach((neededMaterial) => {
      neededMaterial.id = neededMaterial.material.id;
    });
    await modelStore.dispatchUpdateModel(selectedModelId, selectedModel.value);
    toast.success("Pomyślnie zaktualizowano model", {
      timeout: 2000,
    });
    await getModels();
  } catch (error) {
    // Handle error
  }
};

const handlePagination = ({ page, itemsPerPage }) => {
  pagination.value.page = page - 1;
  pagination.value.size = itemsPerPage;
  getModels();
};

watch(
  () => options.value.itemsPerPage,
  (newVal) => {
    pagination.value.size = newVal;
    getModels();
  }
);

onMounted(async () => {
  await getModels();
});
</script>


<template>
  <BasePage title="Konstrukcja maszyn">

    <template #above-card>
      <!-- Add Model Dialog -->
      <v-dialog v-model="addDialogVisible" max-width="1000">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn v-bind="activatorProps" color="primary" class="mb-4" style="max-width: 20rem;" variant="flat">+Dodaj
            nowy model</v-btn>
        </template>
        <v-card title="Dodaj nowy model" rounded="lg">
          <AddModelForm :items="modelToAdd.neededMaterials" :model-value="modelToAdd"
            @on-valid-submit="add(), addDialogVisible = false"></AddModelForm>
        </v-card>
      </v-dialog>
    </template>

    <!-- Edit Model Dialog -->
    <v-dialog v-model="editDialogVisible" max-width="1000">
      <template v-slot:activator="{ props: activatorProps }">
      </template>
      <v-card title="Edytuj model" rounded="lg">
        <EditModelForm :items="selectedModel.neededMaterials" :model-value="selectedModel"
          @on-close="editDialogVisible = false" @on-valid-submit="updateModel"></EditModelForm>
      </v-card>
    </v-dialog>

    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers" :items="items"
      :items-length="options.totalItems" :loading="options.loading" item-value="id" @update:options="handlePagination">
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
