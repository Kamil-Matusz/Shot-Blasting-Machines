<script setup lang="ts"></script>

<template>
  <v-card style="z-index: 100">
    <v-layout>
      <v-navigation-drawer expand-on-hover rail theme="dark" class="bg-black">
        <v-list>
          <v-list-item
            prepend-icon="mdi-account"
            title="Admin"
            subtitle="Imie nazwisko"
          ></v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
          <v-list-item
            prepend-icon="mdi-view-dashboard"
            title="Dashboard"
            to="/dashboard"
          ></v-list-item>
          <v-list-item
            prepend-icon="mdi-account-multiple"
            title="Zarządzanie użytkownikami"
            to="/users-managment"
          ></v-list-item>
          <v-list-item
            prepend-icon="mdi-cog-box"
            title="Konstrukcja maszyn"
            to="/constructions"
          ></v-list-item>
          <v-list-item
            prepend-icon="mdi-playlist-check"
            title="Zamówienia"
            to="/orders"
          ></v-list-item>
          <v-list-item prepend-icon="mdi-store" title="Magazyn" to="/store"></v-list-item>
          <v-list-item prepend-icon="mdi-forklift" title="Produkcja" to="/production"></v-list-item>
          <v-list-item
            prepend-icon="mdi-table-plus"
            title="Dodaj zamówienie"
            to="/orders/create"
          ></v-list-item>
        </v-list>
        <template v-slot:append>
          <v-list-item prepend-icon="mdi-logout" title="Wyloguj" class="my-6"></v-list-item>
        </template>
      </v-navigation-drawer>
    </v-layout>
  </v-card>
</template>
