import { computed } from 'vue'

import { minLength, required } from '@vuelidate/validators'

export const changePasswordRules = computed(() => ({
  oldPassword: { required },
  newPassword: { required, minLength: minLength(5) },
  repeatNewPassword: { required }
}))
