import { CommonModule, DatePipe } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthStore } from '../../core/auth.store';
import { ChatApi, Conversation } from '../../core/chat.api';
import { MeApi, Me } from '../../core/me.api';

type ChatItem =
  | { kind: 'user'; text: string }
  | { kind: 'assistant'; text: string; createdAt?: string };

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './chat.page.html',
  styleUrl: './chat.page.scss'
})
export class ChatPage {
  private readonly chatApi = inject(ChatApi);
  private readonly meApi = inject(MeApi);
  private readonly store = inject(AuthStore);
  private readonly router = inject(Router);

  readonly me = signal<Me | null>(null);
  readonly items = signal<ChatItem[]>([]);
  readonly input = signal('');
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly profileLetter = computed(() => {
    const email = this.store.email() ?? '';
    return (email[0] ?? 'U').toUpperCase();
  });

  constructor() {
    this.refreshMe();
    this.loadHistory();
  }

  refreshMe() {
    this.meApi.me().subscribe({
      next: (m) => this.me.set(m),
      error: () => this.me.set(null)
    });
  }

  loadHistory() {
    this.chatApi.history(0, 20).subscribe({
      next: (page) => {
        const newestFirst = page.content;
        const chronological = [...newestFirst].reverse();
        const mapped: ChatItem[] = chronological.flatMap((c) => [
          { kind: 'user', text: c.message },
          { kind: 'assistant', text: c.response, createdAt: c.createdAt }
        ]);
        this.items.set(mapped);
      }
    });
  }

  send() {
    const msg = this.input().trim();
    if (!msg || this.loading()) return;
    this.error.set(null);
    this.loading.set(true);
    this.items.update((arr) => [...arr, { kind: 'user', text: msg }]);
    this.input.set('');

    this.chatApi.chat(msg).subscribe({
      next: (c: Conversation) => {
        this.items.update((arr) => [...arr, { kind: 'assistant', text: c.response, createdAt: c.createdAt }]);
        this.loading.set(false);
        this.refreshMe();
      },
      error: (e) => {
        this.error.set(e?.error?.detail ?? 'Chat failed');
        this.items.update((arr) => [...arr, { kind: 'assistant', text: 'Désolé, une erreur est survenue.' }]);
        this.loading.set(false);
        this.refreshMe();
      }
    });
  }

  logout() {
    this.store.clear();
    this.router.navigateByUrl('/sign-in');
  }

  goAccount() {
    this.router.navigateByUrl('/account');
  }
}

