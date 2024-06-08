import { defineStore } from 'pinia'
import { API } from '../services'
import { type Order, InputCreateOrder } from '@/models/order'
import type Stats from '../models/stats';
import { ref } from 'vue';

export const useStatsStore = defineStore('statsStore', () => {

    const stats = ref<Stats>();
    const sparkline = ref<Array<number>>()

    async function dispatchGetStats() {
        const { data } = await API.stats.getStats();
        stats.value = data
    }
    async function dispatchGetSparkLine() {
        const { data } = await API.stats.getSparkline();
        sparkline.value = data
    }

    return {
        sparkline,
        stats,
        dispatchGetSparkLine,
        dispatchGetStats
    }
})
