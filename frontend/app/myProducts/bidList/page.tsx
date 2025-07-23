'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import { useRouter, useSearchParams } from 'next/navigation';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import DataTable from '../Components/bidding-table';
import PaginationComponent from '@/components/pagination/Pagination';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

export default function BidsShowPage() {
  const searchParams = useSearchParams();
  const productId = searchParams.get('id');
  const { data: session } = useSession();
  const userToken = session?.user?.token;
  const router = useRouter();

  const currentPage = Number(searchParams.get('page')) || 1;
  const size = Number(searchParams.get('size')) || 10;
  const sortBy = searchParams.get('sortBy') || 'createdAt';
  const sortDirection = searchParams.get('sortDirection') || 'desc';

  const apiPage = currentPage - 1;
  console.log(currentPage);

  const sizeOptions = [2, 5, 10, 15, 20];
  const sortOptions = [
    { value: 'createdAt', label: 'Newest First', direction: 'desc' },
    { value: 'createdAt', label: 'Oldest First', direction: 'asc' },
    { value: 'amount', label: 'Highest Bid', direction: 'desc' },
    { value: 'amount', label: 'Lowest Bid', direction: 'asc' },
    { value: 'bidderName', label: 'Bidder Name A-Z', direction: 'asc' },
    { value: 'bidderName', label: 'Bidder Name Z-A', direction: 'desc' },
  ];

  const params = new URLSearchParams();
  params.set('page', apiPage.toString());
  params.set('size', size.toString());
  params.append('sort', `${sortBy},${sortDirection}`);

  const url = `/api/v2/bid/seller/product/${productId}?${params.toString()}`;
  const url2 = `/api/v2/product/${productId}`;

  const {
    data: bids,
    error,
    isLoading,
  } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));

  const {
    data: products,
    error: error2,
    isLoading: isLoading2,
  } = useSWR(userToken ? [url2, userToken] : null, ([url2, token]) => fetcher(url2, token));

  const bidList = bids?.data?.content || [];
  const product = products?.data;
  const totalPages = bids?.data?.page?.totalPages;
  const total = bids?.data?.page?.totalElements;
  console.log('total pages ' + totalPages);
  console.log('total ' + total);
  console.log(bidList);

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

  if (isLoading || isLoading2) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  if (error || error2) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Error loading products</h1>
        <p>Please try again later.</p>
      </div>
    );
  }


  return (
    <div className="space-y-6 h-screen container mx-12 p-4">
      <div className="flex flex-col justify-between mt-4">
        {/* Show Product Details */}
        {product ? (
          <div className="bg-white p-6 flex">
            <div className="w-1/2">
              <img
                src={product.images[0].imagePath}
                alt={`Product Image`}
                className=" w-[16rem] h-[12rem] object-fill rounded-xl transition-opacity duration-500 ease-in-out"
              />
            </div>
            <div>
              <h1 className="text-3xl font-bold">{product.title}</h1>
              <p className="text-gray-600">{product.description}</p>
              <p className="text-gray-800 font-semibold">Price: ${product.price}</p>
              <p className="text-gray-500">Seller: {product.seller.name}</p>
              <p className="text-3xl text-gray-500">{product.status}</p></div>
          </div>
        ) : (
          <p className="text-red-500">Product details not found.</p>
        )}
        <div className="flex justify-center">
          <h1 className="text-3xl font-semibold mb-6 text-gray-700">Bids List</h1>
        </div>
        {/* Sorting and Filter Controls */}
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
          <DataTable data={bidList} productStatus={product?.status} />
          <div className="p-2 m-2">
            <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
          </div>
        </div>
      </div>
    </div>
  );
}