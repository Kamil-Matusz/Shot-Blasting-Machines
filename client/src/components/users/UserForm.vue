<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { userRules } from '../../validation/rules/userRules';
import { InputCreateUser } from '../../models/user';
import ValidatedTextField from '../ui/ValidatedTextField.vue';
import { onMounted, ref } from 'vue';
import { useRoleStore } from '@/stores/roleStore';
import type { Role } from '@/models/role';

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit']);
const user = defineModel<InputCreateUser>({ required: true });
const validator = useVuelidate(userRules, user);

const roleStore = useRoleStore();

const role = ref("" as string);
const editing = ref(true as boolean);

const submit = async () => {
    console.log('submit')
    validator.value.$touch()
    const result = await validator.value.$validate();
    if(result) {

        let roleToModel = 0;

        for (let i = 0; i < roleStore.roles.length; i++) {
            if (roleStore.roles[i].name === role.value) {
                roleToModel = roleStore.roles[i].id;
                break;
            }
        }

        user.value.role = roleToModel;

        console.log(user.value);
        emit('onValidSubmit');
        return
    }
    emit('onInvalidSubmit');
}


onMounted(async () => {
  await roleStore.dispatchGetRoles();

  for (let i = 0; i < roleStore.roles.length; i++) {
      if (roleStore.roles[i].id === user.value.role) {
          role.value = roleStore.roles[i].name;
          break;
      }
  }

    if (user.value.name === "") editing.value = false;
});

</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">

        <validated-text-field :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.email" label="Email" class="mb-2"></validated-text-field>

        <v-select
            :validation-prop="validator.role"
            label="Rola"
            class="mb-2"
            :items="roleStore.roles.map((r:Role) => r.name)"
            variant="outlined"
            density="compact"
            v-model="role"
            :value="role"
        >

        </v-select>
        

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit"
                :text="editing ? 'Aktualizuj' : 'Dodaj'"
                color="surface-variant"
                variant="flat"
                ></v-btn>
        </v-card-actions>
    </v-form>
</template>
