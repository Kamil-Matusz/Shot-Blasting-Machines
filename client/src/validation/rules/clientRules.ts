import { computed } from 'vue';
import { required, email } from '@vuelidate/validators';

export const clientRules = computed(() => ({
  name: { required },
  email: { required, email },
  phoneNumber: { required },
  address: { required },
}));
