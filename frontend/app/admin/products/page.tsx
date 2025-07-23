'use client';

import React, { useState } from 'react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import ProductRenderer from './ProductRender';
import { useSearchParams } from 'next/navigation';
import { useSession } from 'next-auth/react';

export interface Data {
  id: string;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  status: string;
  seller: {
    id: string;
    name: string;
  };
  condition: string;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
}

export default function ProductViewPage() {
  const searchParams = useSearchParams();
  const { data: session } = useSession();
  const userToken = session?.user?.token;
  const currentPage = Number(searchParams.get('page')) || 1;
  const [filter, setFilter] = useState<string>('ALL');

  const url = filter === 'ALL' ?
    `/api/v2/admin/product/filter?page=${encodeURIComponent(currentPage)}` :
    `/api/v2/admin/product/filter?page=${encodeURIComponent(currentPage)}&status=${encodeURIComponent(filter)}`;

  const { data, error, isLoading } = useSWR(userToken ? [url, userToken] : null, ([url, token]) => fetcher(url, token));
  const products = data?.data?.content || [];
  const totalPages = data?.data?.page.totalPages;

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

  return <ProductRenderer products={products} totalPages={totalPages} currentPage={currentPage} filter={filter}
                          setFilter={setFilter} />;
}