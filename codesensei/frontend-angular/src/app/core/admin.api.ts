import { Injectable, inject } from '@angular/core';
import { ApiClient } from './api.client';
import { Me } from './me.api';

export type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
};

@Injectable({ providedIn: 'root' })
export class AdminApi {
  private readonly api = inject(ApiClient);

  listUsers(page = 0, size = 20) {
    return this.api.get<Page<Me>>(`/api/admin/users?page=${page}&size=${size}`);
  }

  updateUser(id: number, payload: { nom: string; prenom: string; dateNaissance: string }) {
    return this.api.put<Me>(`/api/admin/users/${id}`, payload);
  }

  deleteUser(id: number) {
    return this.api.delete<void>(`/api/admin/users/${id}`);
  }
}

