import { computed } from 'vue';

import { minLength, required, minValue } from '@vuelidate/validators'

export const modelRules = computed (() => ({
    name: { required },
    price: { required, minValue: minValue(1) },
    comments: { required }
  }));