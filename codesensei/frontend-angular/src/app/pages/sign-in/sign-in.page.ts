import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthApi } from '../../core/auth.api';
import { AuthStore } from '../../core/auth.store';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './sign-in.page.html',
  styleUrl: './sign-in.page.scss'
})
export class SignInPage {
  private readonly fb = inject(FormBuilder);
  private readonly api = inject(AuthApi);
  private readonly store = inject(AuthStore);
  private readonly router = inject(Router);

  readonly error = signal<string | null>(null);
  readonly loading = signal(false);

  readonly form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  submit() {
    this.error.set(null);
    if (this.form.invalid) return;
    const { email, password } = this.form.getRawValue();
    this.loading.set(true);
    this.api.login(email!, password!).subscribe({
      next: (res) => {
        this.store.setAuth({ token: res.token, email: res.email, role: res.role, userId: res.userId });
        this.router.navigateByUrl(res.role === 'ADMIN' ? '/admin' : '/chat');
      },
      error: (e) => {
        this.error.set(e?.error?.detail ?? 'Login failed');
        this.loading.set(false);
      }
    });
  }
}

