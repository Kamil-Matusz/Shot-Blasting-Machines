import axios from 'axios';
import { addErrorInterceptor } from './interceptors/errorInterceptor';
import { addJwtInterceptor } from './interceptors/jwtInterceptor';

const httpClient = axios.create({
  baseURL: "http://localhost:8080/api",
});

addErrorInterceptor(httpClient);
addJwtInterceptor(httpClient);

export default httpClient