import { Axios } from "axios";

export function addJwtInterceptor(axiosInstance: Axios) {
    axiosInstance.interceptors.request.use(
        config => {
            const jwtToken = localStorage.getItem('jwtToken');

            if (jwtToken) {
                config.headers.Authorization = `Bearer ${jwtToken}`;
            }
            return config;
        }
    );
}