'use client';

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function PaymentSuccessPage() {
  const router = useRouter();

  useEffect(() => {
    // Redirect manually using router.push() for client-side navigation
    router.push('/payment/payment-success');
  }, [router]);

  return (
    <div className="text-center p-6">
      <h1 className="text-green-600 text-3xl font-bold">Payment Successful ✅</h1>
      <p className="text-lg mt-2">Redirecting to success page...</p>
    </div>
  );
}
