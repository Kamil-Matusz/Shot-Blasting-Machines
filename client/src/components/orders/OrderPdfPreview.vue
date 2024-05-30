<script setup lang="ts">
import { VuePDF, usePDF, PDFSrc } from '@tato30/vue-pdf';
import { useOrdersStore } from '../../stores/ordersStore';
import { onMounted, ref } from 'vue';

const props = defineProps<{ orderId: number }>();

const ordersStore = useOrdersStore();

const orderPDFBlob = await ordersStore.dispatchGetOrderPdfByID(props.orderId);
var blob = new Blob([orderPDFBlob], { type: 'application/pdf' });
const orderPDFUrl = URL.createObjectURL(blob);

const pdfIframe = ref(null)

onMounted(() => {
    if (pdfIframe.value) {
        (pdfIframe.value as any).addEventListener('load', () => {
            const pdfDocument = (pdfIframe.value as any).contentDocument;
            if (pdfDocument) {
                const pdfBody = pdfDocument.querySelector('body');
                if (pdfBody) {
                    pdfBody.style.width = '100%';
                    pdfBody.style.height = '100%';
                }
            }
        });
    }
});
</script>

<template>
    <v-card height="750">
        <iframe style="width: 100%;height:100%" v-if="orderPDFUrl" :src="orderPDFUrl" class="pdf" ref="pdfIframe"></iframe>
    </v-card>
</template>
