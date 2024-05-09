<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { materialRules } from '../../validation/rules/materialRules';
import { InputCreateMaterial, type Material } from '../../models/material';
import ValidatedTextField from '../ui/ValidatedTextField.vue';
import { onMounted, ref } from 'vue';
import { useMaterialsStore } from '@/stores/materialStore';

const materialStore = useMaterialsStore()


const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit']);
const material = defineModel<InputCreateMaterial>({ required: true });
const validator = useVuelidate(materialRules, material);

const items = ref<Material[]>([]);
console.log(items)

const submit = async () => {
    console.log('submit')
    validator.value.$touch()
    const result = await validator.value.$validate();
    if(result) {
        console.log(material.value);
        emit('onValidSubmit');
        return
    }
    emit('onInvalidSubmit');
}

onMounted(async () => {
  // Fetch models from the store
  await materialStore.dispatchGetMaterials();
  // Once data is fetched, assign it to the items ref
  items.value = materialStore.materials;
});
</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">
        <v-select
            v-model="material.name"
            :items="items"
            item-value="name"
            item-text="name"
            label="Nazwa"
            class="mb-2"
        ></v-select>
        <validated-text-field :validation-prop="validator.amount" :is-number="true" label="Ilość" class="mb-2"></validated-text-field>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" text="Dodaj" color="surface-variant" variant="flat"></v-btn>
        </v-card-actions>
    </v-form>
</template>