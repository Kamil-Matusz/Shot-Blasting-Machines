<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue';
import { useClientsStore } from '@/stores/clientStore';
import { useToast } from 'vue-toastification';
import { onMounted, ref } from 'vue';
import ClientForm from '@/components/client/ClientForm.vue';
import { InputCreateClient, type Client } from '@/models/client';

const clientsStore = useClientsStore();
const toast = useToast();

const pagination = ref({
  page: 0,
  size: 10,
});

const headers = [
  { title: 'Id', key: 'id', align: 'start', sortable: false },
  { title: 'Nazwa', key: 'name', align: 'start' },
  { title: 'Email', key: 'email', align: 'start' },
  { title: 'Telefon', key: 'phoneNumber', align: 'start' },
  { title: 'Adres', key: 'address', align: 'start' },
  { title: 'Akcje', key: 'actions', align: 'center', sortable: false },
];

const options = ref({
  itemsPerPage: pagination.value.size,
  loading: false,
  totalItems: clientsStore.totalItems,
});

const clientToAdd = ref(new InputCreateClient());
const clientToEdit = ref({} as Client);
const isActive = ref(false);

const getClients = async () => {
  await clientsStore.dispatchGetClients(pagination.value);
  options.value.totalItems = clientsStore.totalItems;
  options.value.loading = false;
};

onMounted(async () => {
  getClients();
});

const addClient = async () => {
  await clientsStore.dispatchCreateClient(clientToAdd.value)
  .then(() => {
    toast.success("Pomyślnie dodano nowego klienta!");
  })
  clientToAdd.value = new InputCreateClient();
  getClients();
};

const deleteClient = async (id: number) => {
  await clientsStore.dispatchDeleteClient(id);
  toast.success("Pomyślnie usunięto klienta!");
  getClients();
};

const updateClient = async () => {
  await clientsStore.dispatchUpdateClient(clientToEdit.value);
  toast.success("Pomyślnie zaktualizowano dane klienta!");
  getClients();
};

const openEditDialog = (client: Client) => {
  clientToEdit.value = { name: client.name, email: client.email, phoneNumber: client.phoneNumber, address: client.address, id: client.id };
  isActive.value = true;
};

// @ts-ignore
const handlePagination = ({ page, itemsPerPage }) => {
  options.value.loading = true;
  pagination.value.page = page - 1;
  pagination.value.size = itemsPerPage;
  getClients();
};
</script>

<template>
  <BasePage title="Zarządzanie klientami">
    <template #above-card>
      <v-dialog max-width="500">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn v-bind="activatorProps" color="primary" variant="flat" class="mb-4" style="max-width: 20rem;">+Dodaj nowego klienta</v-btn>
        </template>
        <template v-slot:default="{ isActive }">
          <v-card title="Nowy klient" rounded="lg">
            <ClientForm v-model="clientToAdd" @on-valid-submit="addClient(), isActive.value = false"></ClientForm>
          </v-card>
        </template>
      </v-dialog>
    </template>

    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers"
      :items="clientsStore.clients" :items-length="options.totalItems" :loading="options.loading" item-value="name"
      @update:options="handlePagination">
      <template v-slot:item.actions="{ item }" dense>
        <v-btn @click="openEditDialog(item)" rounded="lg" size="small" color="primary" class="ml-2" icon="mdi-pen"></v-btn>
        <v-btn @click="deleteClient(item.id)" rounded="lg" size="small" color="red" class="ml-2" icon="mdi-delete"></v-btn>
      </template>
    </v-data-table-server>

    <v-dialog max-width="500" v-model="isActive">
      <template v-slot:default>
        <v-card title="Edytuj klienta" rounded="lg">
          <ClientForm v-model="clientToEdit" @on-valid-submit="updateClient(), isActive = false"></ClientForm>
        </v-card>
      </template>
    </v-dialog>
  </BasePage>
</template>
