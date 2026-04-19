import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MeApi } from '../../core/me.api';
import { PaymentApi, SubscriptionType } from '../../core/payment.api';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './payment.page.html',
  styleUrl: './payment.page.scss'
})
export class PaymentPage {
  private readonly fb = inject(FormBuilder);
  private readonly payApi = inject(PaymentApi);
  private readonly meApi = inject(MeApi);
  private readonly router = inject(Router);

  readonly loading = signal(false);
  readonly error = signal<string | null>(null);
  readonly ok = signal<string | null>(null);

  readonly form = this.fb.group({
    typeAbonnement: ['PRO' as SubscriptionType, [Validators.required]],
    cardNumber: ['', [Validators.required, Validators.pattern(/^\d{16}$/)]],
    expiry: ['', [Validators.required, Validators.pattern(/^\d{2}\/\d{2}$/)]],
    cvc: ['', [Validators.required, Validators.pattern(/^\d{3,4}$/)]]
  });

  readonly planIds: SubscriptionType[] = ['PRO', 'BUSINESS', 'PRO_PLUS'];

  prices: Record<SubscriptionType, { price: string; credits: number }> = {
    PRO: { price: '10$', credits: 3 },
    BUSINESS: { price: '15$', credits: 7 },
    PRO_PLUS: { price: '20$', credits: 10 }
  };

  submit() {
    this.error.set(null);
    this.ok.set(null);
    if (this.form.invalid) return;
    this.loading.set(true);
    this.payApi.pay(this.form.getRawValue() as any).subscribe({
      next: () => {
        this.meApi.me().subscribe();
        this.ok.set('Payment saved. Your credits were updated.');
        this.loading.set(false);
      },
      error: (e) => {
        this.error.set(e?.error?.detail ?? 'Payment failed');
        this.loading.set(false);
      }
    });
  }

  back() {
    this.router.navigateByUrl('/account');
  }
}

