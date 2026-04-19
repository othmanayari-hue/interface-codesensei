import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '../../core/auth.store';
import { Me, MeApi } from '../../core/me.api';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './account.page.html',
  styleUrl: './account.page.scss'
})
export class AccountPage {
  private readonly api = inject(MeApi);
  private readonly store = inject(AuthStore);
  private readonly router = inject(Router);

  readonly me = signal<Me | null>(null);
  readonly error = signal<string | null>(null);

  constructor() {
    this.api.me().subscribe({
      next: (m) => this.me.set(m),
      error: (e) => this.error.set(e?.error?.detail ?? 'Failed to load account')
    });
  }

  logout() {
    this.store.clear();
    this.router.navigateByUrl('/sign-in');
  }

  goPremium() {
    this.router.navigateByUrl('/payment');
  }

  back() {
    this.router.navigateByUrl('/chat');
  }
}

