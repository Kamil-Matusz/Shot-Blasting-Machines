<script setup lang="ts">
import useVuelidate from '@vuelidate/core'
import { changePasswordRules } from '../../validation/rules/changePasswordRules'
import ValidatedTextField from '../ui/ValidatedTextField.vue'

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit'])
const changePasswordModel = defineModel<InputPasswordChange>({ required: true })
const validator = useVuelidate(changePasswordRules, changePasswordModel)

const submit = async () => {
  console.log('submit')
  validator.value.$touch()
  const result = await validator.value.$validate()
  if (result) {
    console.log(changePasswordModel.value)
    emit('onValidSubmit')
    return
  }
  emit('onInvalidSubmit')
}
</script>

<template>
  <v-form class="pa-4" @submit.prevent="submit">
    <validated-text-field
      type="password"
      :validation-prop="validator.oldPassword"
      label="Stare hasło"
      class="mb-2"
    ></validated-text-field>

    <validated-text-field
      type="password"
      :validation-prop="validator.newPassword"
      label="Nowe hasło"
      class="mb-2"
    ></validated-text-field>

    <validated-text-field
      :validation-prop="validator.repeatNewPassword"
      type="password"
      label="Powtórz nowe hasło"
      class="mb-2"
    ></validated-text-field>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn type="submit" text="Zmień" color="primary" variant="flat"></v-btn>
    </v-card-actions>
  </v-form>
</template>
s
