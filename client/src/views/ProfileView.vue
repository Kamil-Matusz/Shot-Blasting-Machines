<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { InputPasswordChange, type User } from '@/models/user'
import ChangePasswordForm from '@/components/users/ChangePasswordForm.vue'
import { useToast } from 'vue-toastification'
import { useJwtStore } from '@/stores/jwtStore'

const dataToPasswordChange = ref(new InputPasswordChange())
const usersStore = useUserStore()
//@ts-ignore
const user = ref({} as User)
const jwtStore = useJwtStore();

const toast = useToast();

const handleChangePassword = () => {
  usersStore.dispatchChangePassword(dataToPasswordChange.value, user.value.id).then(() => {
    toast.success('Pomyślnie zmieniono hasło!')
    dataToPasswordChange.value = new InputPasswordChange()
  })
}

onMounted(() => {
  // @ts-ignore
    user.value = jwtStore.getUser()

    console.log(user.value);
})
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

            <v-list-item prepend-icon="mdi-pound" title="Identyfikator"> {{ user.id }} </v-list-item>

            <v-list-item prepend-icon="mdi-account" title="Nazwa"> {{ user.name }} </v-list-item>

            <v-list-item prepend-icon="mdi-email" title="Email"> {{ user.email }} </v-list-item>

            <v-list-item prepend-icon="mdi-tie" title="Rola"> {{ jwtStore.getUserRole() }} </v-list-item>
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

