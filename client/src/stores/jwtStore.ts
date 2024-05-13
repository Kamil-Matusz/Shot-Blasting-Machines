import { defineStore } from "pinia";
import { ref } from "vue";
import { API } from "../services";
import { InputJwtToken, InputLoginData, type JwtToken } from '@/models/authorizations';
import { useToast } from "vue-toastification";

const toast = useToast();

export const useJwtStore = defineStore("JwtStore", () => {
  const token = ref<JwtToken>({ jwt: "" });

  //Store private functions that operates on local array to update it after api operations alternatily you can reload whole array with dispatchGetMaterials

  //Functions that operates on api

  async function dispatchLogin(loginData: InputLoginData) {
    try {
      const { data } = await API.jwt.login(loginData);
      token.value.jwt = data.jwt;
      localStorage.setItem("jwtToken", data.jwt);
      toast.success("Zalogowano pomyślnie!");
    } catch (error: any) { // Określenie typu zmiennej error jako any
      if (error.response && error.response.status === 403) {
        toast.error("Nieprawidłowy adres e-mail lub hasło. Spróbuj ponownie.");
      } else {
        toast.error("Wystąpił błąd podczas logowania. Spróbuj ponownie później.");
      }
    }
  }


  return {
    dispatchLogin,
    token
  };
});
