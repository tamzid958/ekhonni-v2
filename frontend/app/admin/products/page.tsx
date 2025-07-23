'use client';

import React from 'react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import { useRouter, useSearchParams } from 'next/navigation';
import { useSession } from 'next-auth/react';
import PaginationComponent from '@/components/pagination/Pagination';
import DataTable from './products-table';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

export interface ProductData {
  id: number;
  title: string;
  subTitle: string;
  description: string;
  price: number;
  division: string;
  address: string;
  condition: string;
  conditionDetails: string;
  status: string;
  createdAt: string;
  seller: {
    id: string;
    name: string;
  };
  category: {
    id: number;
    name: string;
  };
  boostData: {
    boostType: string;
  };
  images: {
    imagePath: string;
  }[];
}

export default function ProductViewPage() {
  const searchParams = useSearchParams();
  const router = useRouter();
  const { data: session } = useSession();
  const userToken = session?.user?.token;

  const currentPage = Number(searchParams.get('page')) || 1;
  const filter = searchParams.get('filter') || 'ALL';
  const size = Number(searchParams.get('size')) || 10;
  const sortBy = searchParams.get('sortBy') || 'newlyListed';


  const url =
    filter === 'ALL'
      ? `/api/v2/admin/product/filter?page=${currentPage}&size=${size}&sortBy=${sortBy}`
      : `/api/v2/admin/product/filter?page=${currentPage}&status=${filter}&size=${size}&sortBy=${sortBy}`;


  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));
  const products = data?.data?.content || [];
  const totalPages = data?.data?.page?.totalPages;
  console.log(products);

  const statusOptions = ['ALL', 'APPROVED', 'PENDING_APPROVAL', 'DECLINED', 'ARCHIVED'];
  const sizeOptions = [5, 10, 15, 20];
  const sortOptions = [
    { value: 'priceLowToHigh', label: 'Price: Low to High' },
    { value: 'priceHighToLow', label: 'Price: High to Low' },
    { value: 'newlyListed', label: 'Newly Listed' },
  ];


  const handleFilterChange = (newFilter: string) => {
    const params = new URLSearchParams(searchParams.toString());

    params.set('filter', newFilter);
    params.set('page', '1'); // Reset page to 1 when filter changes

    router.push(`?${params.toString()}`);
  };

  const handleSizeChange = (newSize: string) => {
    const params = new URLSearchParams(searchParams.toString());

    params.set('size', newSize);
    params.set('page', '1'); // Reset page to 1 when size changes

    router.push(`?${params.toString()}`);
  };

  const handleSortChange = (newSort: string) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set('sortBy', newSort);
    params.set('page', '1'); // Reset page to 1 when sort changes
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
        <h1 className="text-2xl font-bold mb-4">Error loading products</h1>
        <p>Please try again later.</p>
      </div>
    );
  }

  return (
    <div className="space-y-6 h-screen container mx-auto p-4">
      <div className="flex flex-col justify-between mt-4">
        <div>
          <h1 className="text-3xl font-semibold text-gray-700">Products List</h1>
        </div>

        <div className="flex justify-between items-end p-2">
          <Select value={filter} onValueChange={handleFilterChange}>
            <SelectTrigger className="border p-2 m-2 rounded w-auto ml-auto">
              <SelectValue placeholder="Select status" />
            </SelectTrigger>
            <SelectContent>
              {statusOptions.map((status) => (
                <SelectItem key={status} value={status}>
                  {status.replace('_', ' ')}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Select value={String(size)} onValueChange={handleSizeChange}>
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Items per page" />
            </SelectTrigger>
            <SelectContent>
              {sizeOptions.map((option) => (
                <SelectItem key={option} value={String(option)}>
                  {option} items per page
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Select value={sortBy} onValueChange={handleSortChange}>
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              {sortOptions.map((option) => (
                <SelectItem key={option.value} value={option.value}>
                  {option.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="w-full">
          <DataTable data={products} />
          <div className="p-2 m-2">
            <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
          </div>
        </div>
      </div>
    </div>
  );
}
