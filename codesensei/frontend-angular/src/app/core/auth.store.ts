import { Injectable, computed, signal } from '@angular/core';

export type Role = 'ADMIN' | 'USER';

export type AuthState = {
  token: string | null;
  email: string | null;
  role: Role | null;
  userId: number | null;
};

const LS_KEY = 'codesensei_auth';

@Injectable({ providedIn: 'root' })
export class AuthStore {
  private readonly state = signal<AuthState>(this.load());

  readonly token = computed(() => this.state().token);
  readonly role = computed(() => this.state().role);
  readonly email = computed(() => this.state().email);
  readonly isAuthed = computed(() => !!this.state().token);
  readonly isAdmin = computed(() => this.state().role === 'ADMIN');

  setAuth(next: AuthState) {
    this.state.set(next);
    localStorage.setItem(LS_KEY, JSON.stringify(next));
  }

  clear() {
    this.setAuth({ token: null, email: null, role: null, userId: null });
  }

  private load(): AuthState {
    try {
      const raw = localStorage.getItem(LS_KEY);
      if (!raw) return { token: null, email: null, role: null, userId: null };
      const parsed = JSON.parse(raw) as AuthState;
      return parsed ?? { token: null, email: null, role: null, userId: null };
    } catch {
      return { token: null, email: null, role: null, userId: null };
    }
  }
}

