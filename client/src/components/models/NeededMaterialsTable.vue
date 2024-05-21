<script setup lang="ts">
import { ref, watch } from 'vue';
import type { PropType } from 'vue';
import type { Material } from '@/models/material';

const props = defineProps<{
  items: Material[];
}>();

const headers = [
  { text: 'Name', value: 'name' },
  { text: 'Price', value: 'price' },
  { text: 'Amount', value: 'amount' },
  { text: 'Material', value: 'material' },
  { text: 'Actions', value: 'actions', sortable: false }
];

const removeMaterial = (material: Material) => {
  const index = props.items.indexOf(material);
  if (index !== -1) {
    props.items.splice(index, 1); // Remove material locally from the list
  }
};

const amountValidationRule = (value: number) => {
  return value >= 1 || 'Ilość musi być większa niż 0.'; // Validation rule for amount
};
</script>

<template>
  <v-data-table :headers="headers" :items="items" item-key="id" hide-default-footer>
    <template v-slot:item.material="{ item }">
      <span>{{ item.material.name }}</span>
    </template>
    <template v-slot:item.amount="{ item }">
      <v-text-field 
        v-model="item.amount" 
        type="number" 
        outlined 
        dense 
        :rules="[amountValidationRule]" 
      ></v-text-field>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon @click="removeMaterial(item)" color="red">mdi-delete</v-icon>
    </template>
  </v-data-table>
</template>
