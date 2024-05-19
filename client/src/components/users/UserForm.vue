<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { userRules } from '../../validation/rules/userRules';
import { InputCreateUser } from '../../models/user';
import ValidatedTextField from '../ui/ValidatedTextField.vue';
import { onMounted, ref } from 'vue';
import { useRoleStore } from '@/stores/roleStore';
import type { Role } from '@/models/role';
import roles from '@/services/roles';

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit']);
const user = defineModel<InputCreateUser>({ required: true });
const validator = useVuelidate(userRules, user);

const roleStore = useRoleStore();

const dataToSelect = ref([] as any[]);
const dupa = ref("dupa");

const submit = async () => {
    console.log('submit')
    validator.value.$touch()
    const result = await validator.value.$validate();
    if(result) {
        console.log(user.value);
        emit('onValidSubmit');
        return
    }
    emit('onInvalidSubmit');
}

onMounted(async () => {
  await roleStore.dispatchGetRoles();
  let tempTab : any[] = [];
  roleStore.roles.map((r:Role) => tempTab.push({name: r.name, value: r.id}));
  dataToSelect.value = tempTab;
  console.log(dataToSelect);
});

</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">
        <validated-text-field :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.email" label="Email" class="mb-2"></validated-text-field>

        <v-select
            variant="outlined"
            label="Rola" class="mb-2"
            :items="roleStore.roles.map((role: Role) => role.name)"
        >
        
        </v-select>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" text="Dodaj" color="primary" variant="flat"></v-btn>
        </v-card-actions>
    </v-form>
</template>s
