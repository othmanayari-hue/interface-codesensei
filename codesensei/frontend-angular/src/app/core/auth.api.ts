import { Injectable, inject } from '@angular/core';
import { ApiClient } from './api.client';

export type AuthResponse = {
  token: string;
  userId: number;
  email: string;
  role: 'ADMIN' | 'USER';
};

@Injectable({ providedIn: 'root' })
export class AuthApi {
  private readonly api = inject(ApiClient);

  login(email: string, password: string) {
    return this.api.post<AuthResponse>('/api/auth/login', { email, password });
  }

  register(payload: {
    nom: string;
    prenom: string;
    email: string;
    dateNaissance: string;
    password: string;
  }) {
    return this.api.post<AuthResponse>('/api/auth/register', payload);
  }
}

