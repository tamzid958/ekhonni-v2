'use client';

import React from 'react';
import DataTable from '../Components/bidding-table';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import { useSearchParams } from 'next/navigation';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';

export default function BidsShowPage() {
  const searchParams = useSearchParams();
  const productId = searchParams.get('id');
  const { data: session } = useSession();
  const userToken = session?.user?.token;

  const url = `/api/v2/bid/seller/product/${productId}`;

  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));
  const bidList = data?.data?.content || [];
  console.log(bidList);

  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Error loading products</h1>
        <p>Please try again later.</p>
      </div>
    );
  }
  return (
    <div className="bg-white">
      <DataTable data={bidList} />
    </div>
  );
}
