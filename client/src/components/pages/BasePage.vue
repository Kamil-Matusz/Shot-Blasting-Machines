<script setup lang="ts">
defineProps<{
  title: string
}>()
</script>

<template>
  <div class="ml-14 h-screen pa-8 pb-12 d-flex flex-column">
    <h2 class="text-h4 text-black my-4 text-center">{{ title }}</h2>
    <v-card rounded="xl" class="pa-4 flex-grow" elevation="4" style="flex-grow: 1">
      <slot></slot>
    </v-card>
  </div>
</template>
