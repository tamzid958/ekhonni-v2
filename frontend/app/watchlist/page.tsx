import { HorizontalCard } from '@/components/HorizontalCard';
import React from 'react';

// Define the WatchlistItem type for TypeScript
interface WatchlistItem {
  id: number;
  title: string;
  img: string;
  price: number;
  shipping: number;
  timeLeft: string;
  condition: string;
  bidsAmount: number;
  sellerProfile: string;
}

export default async function WatchlistPage() {
  const response = await fetch('http://localhost:3000/api/watchlist', { cache: 'no-store' });

  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }

  const watchlistItems: WatchlistItem[] = await response.json();

  return (
    <div className="bg-[#FAF7F0]">
      <div className="p-6 max-w-4xl mx-auto min-h-screen">
        <h1 className="text-2xl font-bold">My Watchlist</h1>
        <div className="space-y-6">
          {watchlistItems.map((item) => (
            <HorizontalCard key={item.id} watchlistItem={item} />
          ))}
        </div>
      </div>
    </div>

  );
}
