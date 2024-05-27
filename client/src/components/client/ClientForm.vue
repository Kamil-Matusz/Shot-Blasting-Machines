<template>
  <v-form class="pa-4" @submit.prevent="submit">
    <validated-text-field v-model="client.name" :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
    <validated-text-field v-model="client.email" :validation-prop="validator.email" label="Email" class="mb-2"></validated-text-field>
    <validated-text-field v-model="client.phoneNumber" :validation-prop="validator.phoneNumber" label="Telefon" class="mb-2"></validated-text-field>
    <validated-text-field v-model="client.address" :validation-prop="validator.address" label="Adres" class="mb-2"></validated-text-field>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn type="submit" text="Dodaj" color="primary" variant="flat"></v-btn>
    </v-card-actions>
  </v-form>
</template>

<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { required, email } from '@vuelidate/validators';
import { computed, defineEmits, defineProps } from 'vue';
import ValidatedTextField from '@/components/ui/ValidatedTextField.vue'; 
import { InputCreateClient } from '@/models/client';

const props = defineProps({
  modelValue: { type: Object as () => InputCreateClient, required: true }
});

const client = defineModel<InputCreateClient>({ required: true });
const emit = defineEmits(['update:modelValue', 'onValidSubmit', 'onInvalidSubmit']);

const rules = {
  name: { required },
  email: { required, email },
  phoneNumber: { required },
  address: { required },
};

const validator = useVuelidate(rules, client);

const submit = async () => {
  validator.value.$touch();
  const result = await validator.value.$validate();
  if (result) {
    emit('onValidSubmit');
  } else {
    emit('onInvalidSubmit');
  }
};
</script>
