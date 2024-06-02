<script setup lang="ts">
import { useJwtStore } from '@/stores/jwtStore';
import { onMounted,} from 'vue';

const { dispatchLogout, getUserRole } = useJwtStore();

onMounted(() => {
  getUserRole();
});
</script>

<template>
  <v-card style="z-index: 100">
    <v-layout>
      <v-navigation-drawer expand-on-hover rail theme="dark" class="bg-black">
        <v-list>
          <v-list-item
            prepend-icon="mdi-account"
            title="Admin"
            subtitle="Imie nazwisko"
            to="/profile"
          ></v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
          <v-list-item
          v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Sprzedawca'"
            prepend-icon="mdi-view-dashboard"
            title="Dashboard"
            to="/dashboard"
          ></v-list-item>
          <v-list-item
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Sprzedawca'"
            prepend-icon="mdi-account-group"
            title="Klienci"
            to="/clients"
          ></v-list-item>
          <v-list-item
            v-if="getUserRole() === 'Administrator Systemu'"
            prepend-icon="mdi-account-tie"
            title="Zarządzanie użytkownikami"
            to="/users-managment"
          ></v-list-item>
          <v-list-item
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Konstruktor Maszyn'"
            prepend-icon="mdi-cog-box"
            title="Konstrukcja maszyn"
            to="/constructions"
          ></v-list-item>
          <v-list-item
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Sprzedawca'"
            prepend-icon="mdi-table-plus"
            title="Dodaj zamówienie"
            to="/orders/create"
          ></v-list-item>
          <v-list-item
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Sprzedawca' || getUserRole() === 'Pracownik Produkcji'"
            prepend-icon="mdi-playlist-check"
            title="Zamówienia"
            to="/orders"
          ></v-list-item>
          <v-list-item 
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Nadzorca Magazynu'"
            prepend-icon="mdi-store" 
            title="Magazyn" 
            to="/store">
          </v-list-item>
          <v-list-item 
            v-if="getUserRole() === 'Administrator Systemu' || getUserRole() === 'Pracownik Produkcji'"
            prepend-icon="mdi-forklift" 
            title="Produkcja" 
            to="/production">
          </v-list-item>
        </v-list>
        <template v-slot:append>
          <v-list-item prepend-icon="mdi-logout" @click="dispatchLogout" title="Wyloguj" class="my-6"></v-list-item>
        </template>
      </v-navigation-drawer>
    </v-layout>
  </v-card>
</template>
