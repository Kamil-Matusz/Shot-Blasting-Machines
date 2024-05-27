<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { InputPasswordChange } from '@/models/user'
import ChangePasswordForm from '@/components/users/ChangePasswordForm.vue'
import { useToast } from 'vue-toastification'

const dataToPasswordChange = ref(new InputPasswordChange())
const usersStore = useUserStore()

const toast = useToast()

// TODO: Poprawić jak będą dane użytkownika na frontendzie

const handleChangePassword = () => {
  usersStore.dispatchChangePassword(dataToPasswordChange.value, 1).then(() => {
    toast.success('Pomyślnie zmieniono hasło!')
  })
}
</script>

<template>
  <BasePage title="Twój profil">
    <v-row>
      <v-col cols="12" lg="6">
        <v-card
          class="mb-5"
          variant="outlined"
          title="Twoje dane"
          subtitle="Poniżej znajdują się dane Twojego konta."
          cols="12"
          lg="6"
        >
          <v-list density="compact" nav>
            <!-- TODO: Poprawić jak będą dane użytkownika na froncie -->
            <v-list-item prepend-icon="mdi-pound" title="Identyfikator"> 213 </v-list-item>

            <v-list-item prepend-icon="mdi-account" title="Nazwa"> Dupa </v-list-item>

            <v-list-item prepend-icon="mdi-email" title="Email"> example@123.com </v-list-item>

            <v-list-item prepend-icon="mdi-tie" title="Rola"> Administrator </v-list-item>
          </v-list>
        </v-card>
      </v-col>

      <v-col cols="12" lg="6">
        <v-card
          class="mb-5"
          variant="outlined"
          title="Zmiana hasła"
          subtitle="Poniżej możesz zmienić hasło do swojego konta."
          cols="12"
          lg="6"
        >
          <v-dialog max-width="500">
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn
                v-bind="activatorProps"
                color="primary"
                variant="flat"
                class="mb-4 ml-4"
                style="width: 15rem"
                >Zmień hasło</v-btn
              >
            </template>

            <template v-slot:default="{ isActive }">
              <v-card title="Zmień hasło" rounded="lg">
                <ChangePasswordForm
                  v-model="dataToPasswordChange"
                  @on-valid-submit="handleChangePassword(), (isActive.value = false)"
                ></ChangePasswordForm>
              </v-card>
            </template>
          </v-dialog>
        </v-card>
      </v-col>
    </v-row>
  </BasePage>
</template>

