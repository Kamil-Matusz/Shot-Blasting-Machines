<template>
  <div>
    <v-data-table :headers="headers" :items="items" item-key="id" hide-default-footer>
      <template v-slot:item.material="{ item }">
        <span>{{ item.material.name }}</span>
      </template>
      <template v-slot:item.amount="{ item }">
        <v-text-field v-model="item.amount" type="number" outlined dense></v-text-field>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon @click="removeMaterial(item)" color="red">mdi-delete</v-icon>
      </template>
    </v-data-table>
    <v-btn color="primary" @click="saveChanges">Zapisz</v-btn>
  </div>
</template>

<script>
export default {
  props: {
    items: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      headers: [
        { text: 'ID', value: 'id' },
        { text: 'Name', value: 'name' },
        { text: 'Price', value: 'price' },
        { text: 'Amount', value: 'amount' },
        { text: 'Material', value: 'material' }, // New header for material name
        { text: 'Actions', value: 'actions', sortable: false }
      ],
      editedMaterials: [] // To store edited materials locally
    }
  },
  methods: {
    removeMaterial(material) {
      const index = this.items.indexOf(material);
      if (index !== -1) {
        this.items.splice(index, 1); // Remove material locally from the list
      }
    },
    saveChanges() {
      // Emit edited materials to the parent component
      this.$emit('save-changes', this.items);
    }
  }
}
</script>
