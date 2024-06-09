import { type Role } from './role'

export interface User {
  id: number
  name: string
  email: string
  role: Role
}

export class InputCreateUser {
  email: string = ''
  name: string = ''
  role: number = 1
}

export class InputPasswordChange {
  oldPassword: string = ''
  newPassword: string = ''
  repeatNewPassword: string = ''
}
