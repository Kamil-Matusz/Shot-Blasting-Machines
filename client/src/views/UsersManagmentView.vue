<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useUserStore } from '@/stores/userStore';
import { useToast } from 'vue-toastification';
import {onMounted, ref} from 'vue';
import { InputCreateUser, type User } from '@/models/user';
import UserForm from '@/components/users/UserForm.vue';

const usersStore = useUserStore();
const toast = useToast()

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
  { title: 'Akcje', key: 'actions', align: 'center' },
]

//Temporary options to replace with real pagination 
const options = ref({
  itemsPerPage: 10,
  totalItems: 10,
  loading: false
});

const userToAdd = ref(new InputCreateUser());
const userToEdit = ref(new InputCreateUser());
const isActive = ref(false);
const numToEdit = ref(0);

onMounted(async () => {
  await usersStore.dispatchGetUsers();
});

const addUser = async () => {
  await usersStore.dispatchCreateUser(userToAdd.value);
  toast.success("Pomyślnie dodano nowego użytkownika!");
  userToAdd.value = new InputCreateUser();
}

const deleteUser = async (id: number) => {
  await usersStore.dispatchDeleteUser(id);
  toast.success("Pomyślnie usunięto użytkownika!")
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


</script>

<template>
  <BasePage title="Zarządzanie użytkownikami">
    
    <v-dialog max-width="500">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn v-bind="activatorProps" color="surface-variant" variant="flat">+Dodaj nowego użytkownika</v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card title="Nowy użytkownik" rounded="lg">
          <UserForm v-model="userToAdd" @on-valid-submit="addUser(), isActive.value = false"></UserForm>
        </v-card>
      </template>
    </v-dialog>

    <v-data-table-server v-model:items-per-page="options.itemsPerPage" :headers="headers"
      :items="usersStore.users" :items-length="options.totalItems" :loading="options.loading" item-value="name">
      

      <template v-slot:item.actions="{ item }" dense>

        <v-btn @click="openEditDialog(item)" rounded="lg" size="small" color="blue" class="ml-2" icon="mdi-pen"></v-btn>

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
