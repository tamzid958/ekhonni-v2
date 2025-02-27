'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import DataTable from '../my-cart/bidlist-table';
import { useRouter, useSearchParams } from 'next/navigation';
import PaginationComponent from '@/components/pagination/Pagination';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

export default function MyCartPage() {
  const { data: session } = useSession();
  const userToken = session?.user?.token;

  const searchParams = useSearchParams();
  const router = useRouter();

  const currentPage = Number(searchParams.get('page')) || 1;
  const size = Number(searchParams.get('size')) || 10;
  const sortBy = searchParams.get('sortBy') || 'createdAt';
  const sortDirection = searchParams.get('sortDirection') || 'desc';

  const apiPage = currentPage - 1;

  const sizeOptions = [2, 5, 10, 15, 20];
  const sortOptions = [
    { value: 'createdAt', label: 'Newest First', direction: 'desc' },
    { value: 'createdAt', label: 'Oldest First', direction: 'asc' },
    { value: 'amount', label: 'Highest Bid', direction: 'desc' },
    { value: 'amount', label: 'Lowest Bid', direction: 'asc' },
    { value: 'productSellerName', label: 'Seller Name A-Z', direction: 'asc' },
    { value: 'productSellerName', label: 'Seller Name Z-A', direction: 'desc' },
  ];

  const params = new URLSearchParams();
  params.set('page', apiPage.toString());
  params.set('size', size.toString());
  params.append('sort', `${sortBy},${sortDirection}`);

  const url = `/api/v2/bid/bidder?${params.toString()}`;

  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));

  const bidList = data?.data?.content || [];
  const totalPages = data?.data?.page?.totalPages;
  console.log('totalpage:' + totalPages);

  const handleSizeChange = (newSize) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set('size', newSize);
    params.set('page', '1');
    router.push(`?${params.toString()}`);
  };

  const handleSortChange = (selectedLabel) => {
    const selectedOption = sortOptions.find(option => option.label === selectedLabel);
    if (!selectedOption) return;

    const params = new URLSearchParams(searchParams.toString());
    params.set('sortBy', selectedOption.value);
    params.set('sortDirection', selectedOption.direction);
    params.set('page', '1');
    router.push(`?${params.toString()}`);
  };


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
    <div className="space-y-6 h-screen container mx-auto p-4">
      <div className="flex flex-col justify-between mt-4">
        <h1 className="text-3xl font-semibold text-gray-700 text-center">My Cart</h1>

        <div className="flex justify-end items-end p-2">
          <Select value={String(size)} onValueChange={handleSizeChange}>
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Items per page" />
            </SelectTrigger>
            <SelectContent className="right-0">
              {sizeOptions.map((option) => (
                <SelectItem key={option} value={String(option)}>
                  {option} items per page
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Select
            value={sortOptions.find(opt => opt.value === sortBy && opt.direction === sortDirection)?.label}
            onValueChange={handleSortChange}
          >
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Newest First" />
            </SelectTrigger>
            <SelectContent className="right-0">
              {sortOptions.map((option) => (
                <SelectItem key={`${option.value},${option.direction}`} value={option.label}>
                  {option.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

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
