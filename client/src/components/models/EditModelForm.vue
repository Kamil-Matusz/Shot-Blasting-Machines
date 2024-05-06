<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { modelRules } from '../../validation/rules/modelRules';
import { InputCreateModel } from '../../models/model';
import ValidatedTextField from '../ui/ValidatedTextField.vue';

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit']);
const model = defineModel<InputCreateModel>({ required: true });
const validator = useVuelidate(modelRules, model);

const submit = async () => {
    console.log('submit')
    validator.value.$touch()
    const result = await validator.value.$validate();
    if(result) {
        console.log(model.value);
        emit('onValidSubmit');
        return
    }
    emit('onInvalidSubmit');
}

</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">
        <validated-text-field :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.price" :is-number="true" label="Cena" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.comments" label="Uwagi" class="mb-2"></validated-text-field>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" text="Dodaj" color="surface-variant" variant="flat"></v-btn>
        </v-card-actions>
    </v-form>
</template>