<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Navigation from './components/layout/Navigation.vue'
</script>

<template>
  <Navigation></Navigation>
  <router-view v-slot="{ Component, route }">
    <Transition name="slide" mode="out-in">
      <component :key="route.name" :is="Component"></component>
    </Transition>
  </router-view>
</template>

<style>
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease-out;
}

.slide-enter-to {
  transform: translateX(0);
  opacity: 1;
}

.slide-enter-from {
  transform: translateX(-50%);
  opacity: 0;
}

.slide-leave-to {
  transform: translateX(50%);
  opacity: 0;
}

.slide-leave-from {
  transform: translateX(0);
  opacity: 1;
}
</style>
