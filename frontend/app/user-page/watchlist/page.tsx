'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import WatchlistTable from './WatchlistTable';

// Define the WatchlistItem type for TypeScript
interface WatchlistItem {
  id: number;
  title: string;
  price: number;
  condition: string;
  status: string;
  createdAt: string;
  seller: { name: string };
  category: { name: string };
  images: { imagePath: string }[];
}

export default function WatchlistPage() {
  const { data: session, status } = useSession();
  const userToken = session?.user?.token;

  const url = '/api/v2/user/watchlist';
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
    <div className="p-6 max-w-6xl mx-auto min-h-screen bg-white shadow-lg rounded-lg">
      <h1 className="text-2xl font-bold mb-4">My Watchlist</h1>
      <WatchlistTable watchlistItems={watchlistItems} />
    </div>

  );
}