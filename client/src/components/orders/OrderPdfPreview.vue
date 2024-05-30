<script setup lang="ts">
import { VuePDF, usePDF, PDFSrc } from '@tato30/vue-pdf';
import { useOrdersStore } from '../../stores/ordersStore';
import { onMounted, ref } from 'vue';

const props = defineProps<{ orderId: number }>();

const ordersStore = useOrdersStore();

const orderPDFBlob = await ordersStore.dispatchGetOrderPdfByID(props.orderId);
var blob = new Blob([orderPDFBlob], { type: 'application/pdf' }); //this make the magic
const orderPDFUrl = URL.createObjectURL(blob);
//const { pdf, pages } = usePDF(orderPDFUrl);
// const a = document.createElement('a');
// a.href = orderPDFUrl;
// a.target = '_blank';
// a.click();

const pdfIframe = ref(null)

onMounted(() => {
    if (pdfIframe.value) {
        pdfIframe.value.addEventListener('load', () => {
            const pdfDocument = pdfIframe.value.contentDocument;
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
