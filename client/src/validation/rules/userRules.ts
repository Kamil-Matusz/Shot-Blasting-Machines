import { computed } from 'vue'

import { required, email } from '@vuelidate/validators'

export const userRules = computed(() => ({
  name: { required },
  email: { required, email },
  role: { required }
}))
