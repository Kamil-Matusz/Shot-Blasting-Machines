import axios from 'axios';
import { addErrorInterceptor } from './interceptors/errorInterceptor';
import { addJwtInterceptor } from './interceptors/jwtInterceptor';

const httpClient = axios.create({
  baseURL: "api url will go here",
});

addErrorInterceptor(httpClient);
addJwtInterceptor(httpClient);

export default httpClient