<script setup lang="ts">

defineProps<{
    validationProp: any,
    isNumber?: boolean
}>();

</script>

<template>
    <v-text-field v-if="!isNumber" density="compact" variant="outlined" v-model="validationProp.$model" :v-bind="$props"
        :error-messages="validationProp.$errors.map((e: any) => e.$message)" required @blur="validationProp.$touch"
        @input="validationProp.$touch">
    </v-text-field>
    <v-text-field v-else density="compact" variant="outlined" v-model.number="validationProp.$model" :v-bind="$props"
        :error-messages="validationProp.$errors.map((e: any) => e.$message)" required @blur="validationProp.$touch"
        @input="validationProp.$touch">
    </v-text-field>
</template>