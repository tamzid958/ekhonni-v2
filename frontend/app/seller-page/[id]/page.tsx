'use client';

import React, { useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import Loading from '@/components/Loading';

const SellerPage = () => {
  const router = useRouter();
  const pathname = usePathname();

  const id = pathname?.split('/')[2];
  useEffect(() => {
    router.push(`/seller-page/${id}/shop`);
  }, [router]);

  return (
    <div className="min-h-screen bg-gray-100">
      <main className="container mx-auto px-4 py-6">
        <h2 className="text-xl font-semibold"><Loading/></h2>
      </main>
    </div>
  );
};

export default SellerPage;
