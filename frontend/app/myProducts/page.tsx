'use client';

import React, { useState } from 'react';
import MyProductRenderer from './MyProductRenderer';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';

interface ProductData {
  id: number;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  status: string;
  seller: {
    id: string;
    name: string;
  };
  condition: string;
  boostData?: {
    boostType: string;
    boostedAt: string;
    expiresAt: string;
  } | null;
  category: {
    id: number;
    name: string;
  };

  images: {
    imagePath: string;
  }[];
  label: string;
  bids: never;
}


export default function MyProductPage() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const [filter, setFilter] = useState<string>('APPROVED');

  // const selectedCategory = 'All'; // You can modify this dynamically

  // Construct API endpoint
  // const categoryQuery = selectedCategory !== 'All' ? `&categoryName=${encodeURIComponent(selectedCategory)}` : '';
  const url = filter === 'ALL' ?
    `/api/v2/product/user/filter?userId=${encodeURIComponent(userId)}` :
    `/api/v2/product/user/filter?userId=${encodeURIComponent(userId)}&status=${encodeURIComponent(filter)}`;

  console.log(url);
  console.log(userToken);

  // Fetch products using SWR
  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url, userToken));
  const products = data?.data?.content || [];

  console.log(products);

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

  return <MyProductRenderer products={products || []} filter={filter} setFilter={setFilter} />;
}