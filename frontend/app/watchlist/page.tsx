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
  // Fetching the watchlist data directly in the server component
  const response = await fetch('http://localhost:3000/api/watchlist');

  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }

  // Parse the response data into the expected shape
  const watchlistItems: WatchlistItem[] = await response.json();

  return (
    <div className="space-y-6 container mx-auto px-4">
      <h1 className="text-2xl font-bold">My Watchlist</h1>
      {/* Iterate over watchlistItems and render a HorizontalCard for each item */}
      <div className="space-y-6">
        {watchlistItems.map((item) => (
          <HorizontalCard key={item.id} watchlistItem={item} />
        ))}
      </div>
    </div>

  );
}
