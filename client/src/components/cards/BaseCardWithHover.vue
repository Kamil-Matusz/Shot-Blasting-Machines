<template>
    <div class="magicCard">
        <div class="magicCard-content">
            <slot></slot>
        </div>
    </div>
</template>
  
<script setup lang="ts">

</script>

  
<style>
:root {
    --bg-color: ;
    --card-color: rgb(23, 23, 23);
}

.card-container:hover .magicCard::after {
    opacity: 1;
}

.magicCard:hover::before {
    opacity: .5;
}

.magicCard {
    background-color: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    position: relative !important;
    padding: 1rem;
    height: 100%;
}

.magicCard::before,
.magicCard::after {
    border-radius: inherit;
    content: "";
    height: 100%;
    left: 0px;
    opacity: 0;
    position: absolute;
    top: 0px;
    transition: opacity 500ms;
    width: 100%;
}

.magicCard::before {
    background: radial-gradient(800px circle at var(--mouse-x) var(--mouse-y),
            rgba(255, 255, 255, 0.04),
            transparent 40%);
    z-index: 3;
    pointer-events: none;
}

.magicCard::after {

    background: radial-gradient(600px circle at var(--mouse-x) var(--mouse-y),
            rgba(255, 255, 255, 0.4),
            transparent 40%);
    z-index: 1;
}

.magicCard>.magicCard-content {
    background-color: rgb(20, 20, 20);
    border-radius: inherit;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    inset: 1px;
    padding: 1rem;
    position: absolute;
    z-index: 2;
    overflow: auto;
}

.magicCard-content>* {
    pointer-events: auto;
}
</style>