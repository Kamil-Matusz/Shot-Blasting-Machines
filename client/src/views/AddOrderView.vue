<script setup lang="ts">
import BasePage from '@/components/pages/BasePage.vue';
import { useClientsStore } from '@/stores/clientStore';
import { onMounted, ref, watch } from 'vue';
import { type Client } from '@/models/client';

const clientStore = useClientsStore()

const isLoading = ref(false);
const isActive = ref(false);
const isSaved = ref(false);
const isClientSelected = ref(false);

const selectedClient = ref({} as Client);

function confirm() {
  isLoading.value = true;

  setTimeout(() => {
    isLoading.value = false;  
    isSaved.value = true;
  }, 2000);
}

const confirmClientSelect = () => {
  isClientSelected.value = true
}

const selectClient = (value:string) => {
  let splitted = value.split("   ");
  clientStore.clients.forEach((client:Client) => {
    if (splitted[0] == client.name && splitted[1].slice(1,-1) == client.address) {
      selectedClient.value = client;
    }
  })

  console.log(selectedClient.value);
}

const getClients = () => {
  clientStore.dispatchGetClients();
  selectedClient.value = clientStore.clients[0]
}

onMounted(() => {
  getClients()
})
</script>


<template>


<template v-if="isClientSelected">
  <BasePage title="Dodaj zamówienie">

    <v-card class="mb-5"
          variant="outlined"
          title="Klient"
        >

        <v-table class="ml-4 mr-4">
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
              <td>null</td>
              <td>null</td>
              <td>null</td>
              <td>null</td>
              <td>null</td>
            </tr>
          </tbody>
        </v-table>
      
  </v-card>

    <v-card class="mb-5"
      title="Koszt"
      subtitle="Poniżej znajduje się przybliżona wartość zamówienia."
      variant="outlined"
    >
      <h1 class="mb-4 text-center">
        0 PLN
      </h1>
    </v-card>
    <v-row>
      <v-col cols="12" lg="6">
        <v-card class="mb-5"
          variant="outlined"
          title="Śrutownica"
          subtitle="Wybierz z listy rowijanej maszynę, którą chce zamówić klient."
        >
          <v-select
            class="ml-4 mr-4"
            :items="['Śrutownica RB7 (12900 PLN)', 'Śrutownica RB11 (15500 PLN)']"
            label="Wybierz maszynę:"
          />
        </v-card>

        <v-card
          variant="outlined"
          title="Personalizacja"
          subtitle="Wybierz elementy, które życzy sobie klient."
        >
          <v-checkbox class="ml-3"
            hide-details
            label="Hak holowniczy (+ 500 PLN)"
            color="blue"
          />
          <v-checkbox class="ml-3"
            label="System odsysania pyłów (+ 3500 PLN)"
            color="blue"
            hide-details
          />
          <v-checkbox class="ml-3"
            label="Dodatkowy zestaw kół (+ 1000 PLN)"
            color="blue"
            hide-details
          />
        </v-card>

      </v-col>
      <v-col cols="12" lg="6">
        <v-card class="mb-5"
          title="Uwagi"
          subtitle="Zapytaj klienta o dodatkowe uwagi."
          variant="outlined"
        >
          <v-textarea class="ml-4 mr-4"
            clearable
          />
        </v-card>
        <v-card
          title="Podsumowanie"
          subtitle="Przedstaw klientowi wszystkie ustawiania i zapisz zamówienie."
          variant="outlined"
        >
          <p class="ml-4 mr-4">Jeżeli klient potwierdza złożenia zamówienia, zapisz je w bazie danych.</p>
          
        <v-dialog max-width="500" v-model="isActive">
          <template v-slot:activator="{ props: activatorProps }">
            <v-row>
              <v-btn color="primary" class="ma-5" v-bind="activatorProps" text="Zapisz zamówienie" variant="flat"></v-btn>
            </v-row>
          </template>

          <template v-slot:default>
            <template v-if="isLoading">
              <v-card>
                <v-card-text class="ma-5">
                  <v-row class="align-center justify-space-between">
                    Zapisuję zamówienie
                    <v-progress-circular indeterminate color="blue"></v-progress-circular>
                  </v-row>
                </v-card-text>
              </v-card>
            </template>
            <template v-else-if="isSaved">
              <v-card title="Sukces">
                <v-card-text class="ma-5">
                  Zamówienie zostało poprawnie zapisane
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn text="Powrót" @click="isActive = false; isSaved = false"></v-btn>
                </v-card-actions>
              </v-card>
            </template>
            <template v-else>
              <v-card title="Potwierdź">
                <v-card-text>
                  Czy na pewno chcesz zapisać to zamówienie?
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue" class="ma-3" text="Zapisz" variant="flat" @click="confirm"></v-btn>
                  <v-btn text="Anuluj" @click="isActive = false"></v-btn>
                </v-card-actions>
              </v-card>
            </template>

          </template>
        </v-dialog>


        </v-card>
      </v-col>
    </v-row>
  </BasePage>
</template>

<template v-else>
<BasePage title="Wybierz klienta">

  <v-card class="mb-5"
          variant="outlined"
          title="Klient"
          subtitle="Wybierz klienta z listy."
        >

        <v-select
          class="ml-4 mr-4"
          variant="outlined"
          :items="clientStore.clients.map((client: Client) => client.name + '   (' + client.address + ')')"
          @update:modelValue="selectClient"
        ></v-select>

              
  </v-card>

  <v-card class="mb-5"
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
      
  </v-card>

  <v-row>
    <v-col cols="12" lg="6">
      <v-card
          variant="outlined"
          title="Rejestracja klienta"
          subtitle="Jeśli klienta jeszcze nie ma w bazie danych możesz go dodać."
        >
          <v-btn
            class="ma-3"
            color="primary"
          >
            Dodaj klienta do bazy
          </v-btn>
        </v-card>
    </v-col>

    <v-col cols="12" lg="6">
      <v-card
          variant="outlined"
          title="Potwierdzenie"
          subtitle="Potwierdź wybór i przejdź do ekranu zamówienia."
        >
          <v-btn
            class="ma-3"
            color="primary"
            @click="select"
          >
            Potwierdź
          </v-btn>
        </v-card>
    </v-col>
  </v-row>
  

</BasePage>
</template>

</template>
