import { HorizontalCard } from '@/components/HorizontalCard';
import React from 'react';

interface WatchlistItem {
  id: number;
  title: string;
  img: string;
  price: number;
  yourBid: number;
  shipping: number;
  timeLeft: string;
  condition: string;
  bidsAmount: number;
  sellerProfile: string;
}

export default async function CartPage() {
  const response = await fetch('http://localhost:3000/api/cart', { cache: 'no-store' });


  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }
  const watchlistItems: WatchlistItem[] = await response.json();

  return (
    <div className="space-y-6 container mx-auto px-4">
      <h1 className="text-2xl font-bold">My Cart</h1>
      <div className="space-y-6">
        {watchlistItems.map((item) => (
          <HorizontalCard key={item.id} watchlistItem={item} showBidSection={true} showCheckBox={false} />
        ))}
      </div>
    </div>
  );
}
