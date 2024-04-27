import { computed } from 'vue';

import { minLength, required,minValue } from '@vuelidate/validators'

export const materialRules = computed (() => ({
    name: { required },
    amount: { required },
    price: { required},
  }));