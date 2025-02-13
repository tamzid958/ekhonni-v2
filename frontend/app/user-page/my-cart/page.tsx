'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import DataTable from '../my-cart/bidlist-table';
import { useRouter, useSearchParams } from 'next/navigation';
import PaginationComponent from '@/components/pagination/Pagination';

export default function MyCartPage() {
  const { data: session } = useSession();
  const userToken = session?.user?.token;

  const searchParams = useSearchParams();
  const router = useRouter();
  const currentPage = parseInt(searchParams.get('page') || '1', 10);
  const apiPage = currentPage - 1;
  console.log(currentPage);


  const url = `/api/v2/bid/bidder?page=${apiPage}&size=20`;
  const {
    data,
    error,
    isLoading,
  } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));

  const bidList = data?.data?.content || [];
  const totalPages = data?.data?.page?.totalPages;
  const total = data?.data?.page?.totalElements;
  console.log('total pages ' + totalPages);
  console.log('total ' + total);


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
        <h1 className="text-2xl font-bold mb-4">Error loading bids</h1>
        <p>Please try again later.</p>
      </div>
    );
  }
  return (
    <div className="space-y-6 h-screen container mx-12 p-4">
      <div className="flex flex-col justify-between mt-4">
        <div className="flex justify-center">
          <h1 className="text-3xl font-semibold mb-6 text-gray-700">Bids List</h1>
        </div>
        <div className="w-full">
          <DataTable data={bidList} />
          <div className="p-2 m-2">
            <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
          </div>
        </div>
      </div>
    </div>
  );
}