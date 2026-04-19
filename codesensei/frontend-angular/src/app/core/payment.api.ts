import { Injectable, inject } from '@angular/core';
import { ApiClient } from './api.client';

export type SubscriptionType = 'PRO' | 'BUSINESS' | 'PRO_PLUS';

export type Payment = {
  id: number;
  typeAbonnement: SubscriptionType;
  montant: string;
  datePaiement: string;
  cardLast4: string | null;
};

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
};

@Injectable({ providedIn: 'root' })
export class PaymentApi {
  private readonly api = inject(ApiClient);

  pay(payload: {
    typeAbonnement: SubscriptionType;
    cardNumber: string;
    expiry: string;
    cvc: string;
  }) {
    return this.api.post<Payment>('/api/payments', payload);
  }

  list(page = 0, size = 20) {
    return this.api.get<Page<Payment>>(`/api/payments?page=${page}&size=${size}`);
  }
}

