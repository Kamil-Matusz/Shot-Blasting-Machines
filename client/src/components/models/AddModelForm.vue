<script setup lang="ts">
import useVuelidate from '@vuelidate/core';
import { modelRules } from '../../validation/rules/modelRules';
import { InputEditModel } from '../../models/model';
import ValidatedTextField from '../ui/ValidatedTextField.vue';
import NeededMaterialsTable from './NeededMaterialsTable.vue';
import { ref, defineEmits, defineProps, onMounted } from 'vue';
import type { Material } from '@/models/material';
import { useMaterialsStore } from '@/stores/materialStore';

const emit = defineEmits(['onValidSubmit', 'onInvalidSubmit', 'addModel']);
const model = defineModel<InputEditModel>({ required: true });
const validator = useVuelidate(modelRules, model);
const selectedMaterial = ref<Material | null>(null);
const store = useMaterialsStore();

const submit = async () => {
    validator.value.$touch();
    const result = await validator.value.$validate();
    if (result) {
        emit('onValidSubmit');
        emit('addModel', model.value);
        return;
    }
    emit('onInvalidSubmit');
}

const props = defineProps<{
    items: Material[]; 
}>();


const addMaterial = () => {
    if (selectedMaterial.value) {
        const existingItemIndex = props.items.findIndex(item => item.material.id === selectedMaterial.value.id);
        if (existingItemIndex !== -1) {
            // If item already exists, update the amount
            props.items[existingItemIndex].amount = parseInt(props.items[existingItemIndex].amount) + 1; // Convert to number and increment
        } else {
            // If item does not exist, add it to the array
            const newMaterial = { material: selectedMaterial.value, amount: 1 }; // Set initial amount to 1
            props.items.push(newMaterial);
        }
    }
}

onMounted(async () => {
    try {
        await store.dispatchGetMaterials();
        console.log(store.materials)
    } catch (error) {
        // 
    }
});
</script>

<template>
    <v-form class="pa-4" @submit.prevent="submit">
        <validated-text-field :validation-prop="validator.name" label="Nazwa" class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.price" :is-number="true" label="Cena"
            class="mb-2"></validated-text-field>
        <validated-text-field :validation-prop="validator.comments" label="Uwagi" class="mb-2"></validated-text-field>

        <v-container fluid>
            <v-layout row wrap>
                <NeededMaterialsTable :items="props.items"></NeededMaterialsTable>
            </v-layout>

            <v-select v-model="selectedMaterial" :items="store.materials" label="Wybierz materiał" class="mb-2"
                item-title="name" item-value="id" return-object></v-select>

            <v-btn text="Dodaj materiał" @click="addMaterial" color="surface-variant" variant="flat"></v-btn>
        </v-container>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" text="Dodaj model" color="primary" variant="flat"></v-btn> <!-- Change button text -->
        </v-card-actions>
    </v-form>
</template>