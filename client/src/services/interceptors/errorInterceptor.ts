import { Axios } from "axios";
import { useToast } from 'vue-toastification';
import router from '../../router/index';

const toast = useToast();

export function addErrorInterceptor(axiosInstance: Axios) {
    axiosInstance.interceptors.response.use(
        response => {
            return response;
        },
        error => {
            if (error.response) {
                switch (error.response.status) {
                    case 400:
                        toast.error(error.response.data);
                        break;
                    case 401:
                        toast.error("Bład autoryzacji");
                        router.push('/')
                        break;
                    case 403:
                        toast.error("Brak dostępu");
                        router.push('/')
                        break;
                    case 404:
                        toast.error("Nieznaleziono");
                        router.push('/')
                        break;
                    case 500:
                        toast.error("Wewnętrzny bład serwera");
                        break;
                    default:
                        toast.error("Wystąpił nieoczekiwany błąd");
                }
            } else if (error.request) {
                toast.error("Brak odpowiedzi");
            } else {
                toast.error("Bład podczas wysyłania żądania");
            }

            return Promise.reject(error);
        }
    );
}