<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useUserStore } from '@/stores/userStore';
import { useToast } from 'vue-toastification';
import {onMounted, ref} from 'vue';
import { InputCreateUser, type User } from '@/models/user';
import UserForm from '@/components/users/UserForm.vue';
import { type PaginationParams, InputPagination } from '@/models/paginationParams';

const usersStore = useUserStore();
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
  { title: 'Email', key: 'email', align: 'start' },
  { title: 'Rola', key: 'role.name', align: 'start'},
  { title: 'Akcje', key: 'actions', align: 'center', sortable: false },
]

//Temporary options to replace with real pagination 
const options = ref({
  itemsPerPage: pagination.value.size,
  loading: true,
  totalItems: 0
});

const userToAdd = ref(new InputCreateUser());
const userToEdit = ref(new InputCreateUser());
const isActive = ref(false);
const numToEdit = ref(0);

const getUsers = async () => {
  await usersStore.dispatchGetUsers(pagination.value);
  options.value.totalItems = usersStore.totalItems;
  options.value.loading = false;
}

onMounted(async () => {
  getUsers();
});

const addUser = async () => {
  await usersStore.dispatchCreateUser(userToAdd.value);
  toast.success("Pomyślnie dodano nowego użytkownika!");
  userToAdd.value = new InputCreateUser();
  getUsers();
}

const deleteUser = async (id: number) => {
  await usersStore.dispatchDeleteUser(id);
  toast.success("Pomyślnie usunięto użytkownika!");
  getUsers();
}

const updateUser = async (id: number) => {
  await usersStore.dispatchUpdateUser(userToEdit.value, id);
  toast.success("Pomyślnie zaktualizowano dane użytkownika!")
}

const openEditDialog = (user: User) => {
  userToEdit.value = {name: user.name, email: user.email, role: user.role.id};
  numToEdit.value = user.id;
  isActive.value = true;
};

// @ts-ignore
const handlePagination = ({ page, itemsPerPage }) => {
  options.value.loading = true;
  pagination.value.page = page-1;
  pagination.value.size = itemsPerPage;
  getUsers();
}


</script>

<template>
  <BasePage title="Zarządzanie użytkownikami">
    
    <template #above-card>
    <v-dialog max-width="500">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn v-bind="activatorProps" color="primary" variant="flat" class="mb-4" style="max-width: 20rem;">+Dodaj nowego użytkownika</v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card title="Nowy użytkownik" rounded="lg">
          <UserForm v-model="userToAdd" @on-valid-submit="addUser(), isActive.value = false"></UserForm>
        </v-card>
      </template>
    </v-dialog>
  </template>

    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers"
      :items="usersStore.users" :items-length="options.totalItems" :loading="options.loading" item-value="name"
      @update:options="({ page, itemsPerPage }) => handlePagination({ page, itemsPerPage })">
      

      <template v-slot:item.actions="{ item }" dense>

        <v-btn @click="openEditDialog(item)" rounded="lg" size="small" color="primary" class="ml-2" icon="mdi-pen"></v-btn>

        <v-btn @click="deleteUser(item.id)" rounded="lg" size="small" color="red" class="ml-2"
          icon="mdi-delete"></v-btn>

      </template>

    </v-data-table-server>

    <v-dialog max-width="500" v-model="isActive">
          <template v-slot:activator="">
            
          </template>

          <template v-slot:default>
            <v-card title="Edytuj użytkownika" rounded="lg">
              <UserForm v-model="userToEdit" @on-valid-submit="updateUser(numToEdit), isActive = false"></UserForm>
            </v-card>
          </template>
        </v-dialog>

  </BasePage>
</template>
