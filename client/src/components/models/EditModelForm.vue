<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { modelRules } from '../../validation/rules/modelRules';
import { InputEditModel } from '../../models/model';
import ValidatedTextField from '../ui/ValidatedTextField.vue';
import NeededMaterialsTable from './NeededMaterialsTable.vue';
import { ref } from 'vue';
import type { Material } from '@/models/material';

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit']);
const model = defineModel<InputEditModel>({ required: true });
const validator = useVuelidate(modelRules, model);

const submit = async () => {
    console.log('submit')
    validator.value.$touch()
    const result = await validator.value.$validate();
    if (result) {
        console.log(model.value);
        emit('updateModel');
        return
    }
    emit('onInvalidSubmit');
}


// Define the props
const props = defineProps<{
    items: Material[]; // Define the type as per your data structure
}>();

</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">
        <validated-text-field :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.price" :is-number="true" label="Cena"
            class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.comments" label="Uwagi" class="mb-2"></validated-text-field>

        <v-container fluid>
            <v-layout row wrap>
                <!-- Include the NeededMaterialsTable component and pass the items as a prop -->
                <NeededMaterialsTable :items="props.items"></NeededMaterialsTable>
            </v-layout>
        </v-container>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" text="Zapisz" color="surface-variant" variant="flat"></v-btn>
        </v-card-actions>
    </v-form>
</template>