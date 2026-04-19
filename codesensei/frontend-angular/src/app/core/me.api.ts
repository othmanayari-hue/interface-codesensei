import { Injectable, inject } from '@angular/core';
import { ApiClient } from './api.client';

export type Me = {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: 'ADMIN' | 'USER';
  dateNaissance: string;
  abonnement: 'NONE' | 'PRO' | 'BUSINESS' | 'PRO_PLUS';
  nombreConversationsRestantes: number;
};

@Injectable({ providedIn: 'root' })
export class MeApi {
  private readonly api = inject(ApiClient);
  me() {
    return this.api.get<Me>('/api/me');
  }
}

