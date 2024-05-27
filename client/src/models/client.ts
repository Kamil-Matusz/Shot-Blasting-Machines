export interface Client {
    id:number
    name:string
    email:string
    phoneNumber:string
    address:string
}

export class InputCreateClient {
    name: string = ''
    email: string = ''
    phoneNumber: string = ''
    address: string = ''
  }
