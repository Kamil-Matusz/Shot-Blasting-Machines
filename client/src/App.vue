<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { useJwtStore } from '@/stores/jwtStore';
import { onMounted } from 'vue';
import Navigation from './components/layout/Navigation.vue'

const { isJwtTokenExists } = useJwtStore();

const handleMouseMove = (e: MouseEvent) => {
  const cards = document.querySelectorAll('.magicCard');
  cards.forEach((card: HTMLElement) => {
    const rect = card.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    card.style.setProperty('--mouse-x', `${x}px`);
    card.style.setProperty('--mouse-y', `${y}px`);
  });
};


onMounted(() => {
  isJwtTokenExists();
});
</script>

<template>
  <Navigation v-if="isJwtTokenExists() === true"></Navigation>
  <Suspense>
    <router-view v-slot="{ Component, route }">
      <div class="card-container" @mousemove="handleMouseMove" ref="cardContainer">
        <Transition name="slide" mode="out-in">
          <component :key="route.name" :is="Component"></component>
        </Transition>
      </div>
    </router-view>
  </Suspense>
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
