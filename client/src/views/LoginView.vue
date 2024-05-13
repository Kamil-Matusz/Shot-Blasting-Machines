<script setup lang="ts">
import { InputLoginData } from '@/models/authorizations';
import { useJwtStore } from '@/stores/jwtStore';
import { ref } from 'vue';

const jwtStore = useJwtStore();

const loginData = ref(new InputLoginData());

const handleLogin = () => {
  jwtStore.dispatchLogin(loginData.value);
}

</script>

<template>
  <body class="d-flex align-center justify-center">
    <v-container >
      <v-row justify="center">
        <v-col cols="12" sm="8" md="6">
          <v-card>
            <v-card-title  class="headline text-center">Logowanie</v-card-title>
            <v-card-text>
              <v-form class="text-center">
                <v-text-field v-model="loginData.email" label="E-mail użytkownika"></v-text-field>
                <v-text-field v-model="loginData.password" label="Hasło" type="password"></v-text-field>
                <v-btn color="primary" @click.prevent="handleLogin">Zaloguj</v-btn>
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </body>
</template>
