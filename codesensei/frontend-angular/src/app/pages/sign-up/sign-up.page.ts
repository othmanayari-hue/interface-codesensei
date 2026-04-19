import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthApi } from '../../core/auth.api';
import { AuthStore } from '../../core/auth.store';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './sign-up.page.html',
  styleUrl: './sign-up.page.scss'
})
export class SignUpPage {
  private readonly fb = inject(FormBuilder);
  private readonly api = inject(AuthApi);
  private readonly store = inject(AuthStore);
  private readonly router = inject(Router);

  readonly error = signal<string | null>(null);
  readonly loading = signal(false);

  readonly form = this.fb.group({
    nom: ['', [Validators.required]],
    prenom: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    dateNaissance: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  submit() {
    this.error.set(null);
    if (this.form.invalid) return;
    const v = this.form.getRawValue();
    this.loading.set(true);
    this.api
      .register({
        nom: v.nom!,
        prenom: v.prenom!,
        email: v.email!,
        dateNaissance: v.dateNaissance!,
        password: v.password!
      })
      .subscribe({
        next: (res) => {
          this.store.setAuth({ token: res.token, email: res.email, role: res.role, userId: res.userId });
          this.router.navigateByUrl('/chat');
        },
        error: (e) => {
          this.error.set(e?.error?.detail ?? 'Registration failed');
          this.loading.set(false);
        }
      });
  }
}

