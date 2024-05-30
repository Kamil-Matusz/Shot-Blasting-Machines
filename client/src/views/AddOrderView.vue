<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue'
import { useClientsStore } from '@/stores/clientStore'
import { onMounted, ref } from 'vue'
import { type Client, InputCreateClient } from '@/models/client'
import { useModelStore } from '@/stores/modelStore'
import { useAccessoryStore } from '@/stores/accessoryStore'
import type { Model } from '@/models/model'
import { InputCreateOrder } from '@/models/order'
import type { Accessory } from '@/models/accessory'
import { useOrderStore } from '@/stores/orderStore'
import { InputPagination } from '@/models/paginationParams'
import { useToast } from 'vue-toastification'
import BaseCardWithHover from '@/components/cards/BaseCardWithHover.vue'

const clientStore = useClientsStore()
const modelStore = useModelStore()
const accessoryStore = useAccessoryStore()
const orderStore = useOrderStore()
const toast = useToast()

const clientToAdd = ref(new InputCreateClient())

const isClientSelected = ref(false)

const selectedClient = ref({} as Client)
const selectedModel = ref({} as Model)

const newOrderToSave = ref(new InputCreateOrder() as InputCreateOrder)

const price = ref(0 as number)

const confirmClientSelect = () => {
  if (selectedClient.value.id) {
    isClientSelected.value = true
    newOrderToSave.value.client = selectedClient.value.id
    getModels()
    getAccessories()
  }
  else {
    toast.error("Najpierw wybierz klienta!");
  }
}

const selectClient = (value: string) => {
  let splitted = value.split('   ')

  clientStore.clients.forEach((client: Client) => {
    if (splitted[0] == client.name && splitted[1].slice(1, -1) == client.address) {
      selectedClient.value = client
    }
  })
}

const changeCheckbox = (checkValue: boolean, accessoryId: number) => {
  let tempAccessory: Accessory = { id: 0, name: '', price: 0 }

  accessoryStore.accessories.forEach((a: Accessory) => {
    if (a.id === accessoryId) tempAccessory = a
  })

  if (checkValue) {
    price.value += tempAccessory.price
    newOrderToSave.value.accessories.push(tempAccessory.id)
  } else {
    price.value -= tempAccessory.price
    const index = newOrderToSave.value.accessories.indexOf(tempAccessory.id)
    newOrderToSave.value.accessories.splice(index, 1)
  }
}

const selectModel = (value: string) => {
  if (selectedModel.value.price) {
    price.value -= selectedModel.value.price
  }

  let splitted = value.split('  ')
  modelStore.models.forEach((model: Model) => {
    if (splitted[0] == model.name && splitted[1].slice(1, -5) == model.price.toString()) {
      selectedModel.value = model
      price.value += model.price
      newOrderToSave.value.model = selectedModel.value.id
    }
  })
}

const getClients = () => {
  clientStore.dispatchGetClients()
}

const getModels = () => {
  let pagination = new InputPagination()
  pagination.page = 0
  pagination.size = 100
  modelStore.dispatchGetModels(pagination)
}

const getAccessories = () => {
  accessoryStore.dispatchGetAccesories()
}

const addClient = async () => {
  await clientStore.dispatchCreateClient(clientToAdd.value)
  .then(() => {
    toast.success("Pomyślnie dodano nowego klienta!");
    clientToAdd.value = new InputCreateClient();
  })
  };

// TODO - Poprawić jak będą dane użytkownika na frontendzie
const saveOrder = () => {
  if (newOrderToSave.value.model !== 0) {
    newOrderToSave.value.price = price.value
    newOrderToSave.value.date = new Date().toISOString().slice(0, 19).replace('T', ' ').toString()

    newOrderToSave.value.user = 1 // Tu do zmiany!

    console.log(newOrderToSave.value)

    orderStore.dispatchCreateOrder(newOrderToSave.value).then(() => {
      toast.success('Pomyślnie dodano nowe zamówienia!')
      router.push('/orders')
    })
  }
  else {
    toast.error("Najpierw wybierz model maszyny!")
  }
}

onMounted(() => {
  getClients()
})
</script>

<template>
  <template v-if="isClientSelected">
    <BasePage title="Dodaj zamówienie">
      <BaseCardWithHover class="mb-5" variant="outlined" title="Klient">
        <v-table class="ml-4 mr-4 mb-4">
          <thead>
            <tr>
              <th>ID</th>
              <th>Imię i nazwisko</th>
              <th>Email</th>
              <th>Numer telefonu</th>
              <th>Adres</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ selectedClient.id }}</td>
              <td>{{ selectedClient.name }}</td>
              <td>{{ selectedClient.email }}</td>
              <td>{{ selectedClient.phoneNumber }}</td>
              <td>{{ selectedClient.address }}</td>
            </tr>
          </tbody>
        </v-table>
      </BaseCardWithHover>

      <BaseCardWithHover
        class="mb-5"
        title="Koszt"
        subtitle="Poniżej znajduje się przybliżona wartość zamówienia."
        variant="outlined"
      >
        <h1 class="mb-4 text-center">{{ price }} PLN</h1>
      </BaseCardWithHover>
      <v-row>
        <v-col cols="12" lg="6">
          <BaseCardWithHover
            class="mb-5"
            variant="outlined"
            title="Śrutownica"
            subtitle="Wybierz z listy rowijanej model maszyny, który chce zamówić klient."
          >
            <v-select
              class="ml-4 mr-4"
              variant="outlined"
              :items="
                modelStore.models.map((model: Model) => model.name + '  (' + model.price + ' PLN)')
              "
              @update:modelValue="selectModel"
            />
          </BaseCardWithHover>

          <BaseCardWithHover
            variant="outlined"
            title="Personalizacja"
            subtitle="Wybierz elementy, które życzy sobie klient."
          >
            <v-checkbox
              class="ml-3"
              v-for="accessory in accessoryStore.accessories"
              hide-details
              :label="accessory.name + ' (+ ' + accessory.price + ' PLN)'"
              color="primary"
              :key="accessory.id"
              @change="(event: any) => changeCheckbox(event.srcElement.checked, accessory.id)"
            />
          </BaseCardWithHover>
        </v-col>
        <v-col cols="12" lg="6">
          <BaseCardWithHover
            class="mb-5"
            title="Uwagi"
            subtitle="Zapytaj klienta o dodatkowe uwagi."
            variant="outlined"
          >
            <v-textarea class="ml-4 mr-4" v-model="newOrderToSave.comments" clearable />
          </BaseCardWithHover>
          <BaseCardWithHover
            title="Podsumowanie"
            subtitle="Przedstaw klientowi wszystkie ustawienia i zapisz zamówienie."
            variant="outlined"
            class="pb-1"
          >
            <p class="ml-4 mr-4">
              Jeżeli klient potwierdza złożenia zamówienia, zapisz je w bazie danych.
            </p>

            <v-btn
              color="primary"
              class="ma-5"
              text="Zapisz zamówienie"
              @click="saveOrder"
              variant="flat"
            ></v-btn>
          </BaseCardWithHover>
        </v-col>
      </v-row>
    </BasePage>
  </template>

  <template v-else>
    <BasePage title="Wybierz klienta">
      <BaseCardWithHover class="mb-5" variant="outlined" title="Klient" subtitle="Wybierz klienta z listy.">
        <v-select
          class="ml-4 mr-4"
          variant="outlined"
          :items="
            clientStore.clients.map((client: Client) => client.name + '   (' + client.address + ')')
          "
          @update:modelValue="selectClient"
        ></v-select>
      </BaseCardWithHover>

      <BaseCardWithHover
        class="mb-5"
        variant="outlined"
        title="Informacje o kliencie"
        subtitle="Przed potwierdzeniem zapytaj klienta, czy dane w bazie są poprawne."
      >
        <v-table class="ml-4 mr-4 mb-4">
          <thead>
            <tr>
              <th>ID</th>
              <th>Imię i nazwisko</th>
              <th>Email</th>
              <th>Numer telefonu</th>
              <th>Adres</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ selectedClient.id }}</td>
              <td>{{ selectedClient.name }}</td>
              <td>{{ selectedClient.email }}</td>
              <td>{{ selectedClient.phoneNumber }}</td>
              <td>{{ selectedClient.address }}</td>
            </tr>
          </tbody>
        </v-table>
      </BaseCardWithHover>

      <v-row>
        <v-col cols="12" lg="6">
          <BaseCardWithHover
            variant="outlined"
            title="Rejestracja klienta"
            subtitle="Jeśli klienta jeszcze nie ma w bazie danych możesz go dodać."
          >
          <v-dialog max-width="500">
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn v-bind="activatorProps" color="primary" variant="flat" class="mb-4 ml-4" style="max-width: 20rem;">Dodaj klienta do bazy</v-btn>
            </template>
            <template v-slot:default="{ isActive }">
              <v-card title="Nowy klient" rounded="lg">
                <ClientForm v-model="clientToAdd" @on-valid-submit="addClient(), isActive.value = false"></ClientForm>
              </v-card>
            </template>
          </v-dialog>
          </BaseCardWithHover>
        </v-col>

        <v-col cols="12" lg="6">
          <BaseCardWithHover
            variant="outlined"
            title="Potwierdzenie"
            subtitle="Potwierdź wybór i przejdź do ekranu zamówienia."
          >
            <v-btn class="ma-3" color="primary" @click="confirmClientSelect"> Potwierdź </v-btn>
          </BaseCardWithHover>
        </v-col>
      </v-row>
    </BasePage>
  </template>
</template>
