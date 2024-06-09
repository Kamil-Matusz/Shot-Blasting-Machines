import httpClient from "../httpClient";
import { type JwtToken, InputJwtToken, InputLoginData } from "@/models/authorizations";

const base = 'login';

async function login(input: InputLoginData) {
  return await httpClient.post<JwtToken>(base, input);
}

export default {
    login
};
