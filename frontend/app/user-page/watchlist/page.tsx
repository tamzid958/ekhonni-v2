'use client';

import { HorizontalCard } from '@/components/HorizontalCard';
import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';

// Define the WatchlistItem type for TypeScript
interface WatchlistItem {
  id: number;
  title: string;
  img: string;
  price: number;
  yourBid?: number;
  shipping: number;
  timeLeft: string;
  condition: string;
  bidsAmount: number;
  sellerProfile: string;
}

export default function WatchlistPage() {
  const { data: session, status } = useSession();
  const userToken = session?.user?.token;

  const url = '/api/v2/users/watchlist';
  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));
  console.log(data?.data);

  const watchlistItems: WatchlistItem[] = data?.data?.content || [];

  if (status === 'loading' || isLoading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  if (!session || error) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
        <p>You need to be signed in to view this page.</p>
      </div>
    );
  }

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
