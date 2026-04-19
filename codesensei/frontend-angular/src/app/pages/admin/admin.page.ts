import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminApi } from '../../core/admin.api';
import { AuthStore } from '../../core/auth.store';
import { Me } from '../../core/me.api';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin.page.html',
  styleUrl: './admin.page.scss'
})
export class AdminPage {
  private readonly api = inject(AdminApi);
  private readonly store = inject(AuthStore);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  readonly users = signal<Me[]>([]);
  readonly page = signal(0);
  readonly totalPages = signal(0);
  readonly error = signal<string | null>(null);

  readonly editId = signal<number | null>(null);
  readonly form = this.fb.group({
    nom: ['', [Validators.required]],
    prenom: ['', [Validators.required]],
    dateNaissance: ['', [Validators.required]]
  });

  constructor() {
    this.load();
  }

  load(p = this.page()) {
    this.error.set(null);
    this.api.listUsers(p, 20).subscribe({
      next: (res) => {
        this.users.set(res.content);
        this.page.set(res.number);
        this.totalPages.set(res.totalPages);
      },
      error: (e) => this.error.set(e?.error?.detail ?? 'Failed to load users')
    });
  }

  startEdit(u: Me) {
    this.editId.set(u.id);
    this.form.patchValue({
      nom: u.nom,
      prenom: u.prenom,
      dateNaissance: (u.dateNaissance ?? '').slice(0, 10)
    });
  }

  cancelEdit() {
    this.editId.set(null);
  }

  save() {
    const id = this.editId();
    if (!id || this.form.invalid) return;
    this.api.updateUser(id, this.form.getRawValue() as any).subscribe({
      next: () => {
        this.editId.set(null);
        this.load();
      },
      error: (e) => (this.error.set(e?.error?.detail ?? 'Update failed'), null)
    });
  }

  del(id: number) {
    this.api.deleteUser(id).subscribe({
      next: () => this.load(),
      error: (e) => this.error.set(e?.error?.detail ?? 'Delete failed')
    });
  }

  logout() {
    this.store.clear();
    this.router.navigateByUrl('/sign-in');
  }
}

