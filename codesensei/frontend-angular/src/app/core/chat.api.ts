import { Injectable, inject } from '@angular/core';
import { ApiClient } from './api.client';

export type Conversation = {
  id: number;
  message: string;
  response: string;
  createdAt: string;
};

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
};

@Injectable({ providedIn: 'root' })
export class ChatApi {
  private readonly api = inject(ApiClient);

  history(page = 0, size = 20) {
    return this.api.get<Page<Conversation>>(`/api/conversations?page=${page}&size=${size}`);
  }

  chat(message: string) {
    return this.api.post<Conversation>('/api/conversations/chat', { message });
  }
}

