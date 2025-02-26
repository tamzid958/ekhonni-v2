'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import { HorizontalCard } from '@/components/HorizontalCard';


export default function WatchlistPage() {
  const { data: session, status } = useSession();
  const userToken = session?.user?.token;

  const url = '/api/v2/user/watchlist';
  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));
  console.log(data?.data);

  const watchlistItems = data?.data?.content || [];

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
    <div className="min-h-screen p-6 max-w-6xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">My Watchlist</h1>
      <div className="space-y-6 ">
        {watchlistItems.length > 0 ? (
          watchlistItems.map((item) => <HorizontalCard key={item.id} watchlistItem={item} token={userToken} />)
        ) : (
          <p className="text-gray-500">No items in your watchlist.</p>
        )}
      </div>
    </div>

  );
}
