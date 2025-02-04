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

// async function fetchMyProduct(userId: string, selectedCategory: string): Promise<ProductData[]> {
//   const category = selectedCategory || 'All';
//   const url =
//     category === 'All'
//       ? `http://localhost:8080/api/v2/product/user/filter`
//       : `http://localhost:8080/api/v2/product/user/filter?userId=${encodeURIComponent(userId)}&categoryName=${encodeURIComponent(selectedCategory)}`;
//
//   try {
//     const response = await fetch(url, { cache: 'no-store' });
//     if (!response.ok) {
//       throw new Error('Failed to fetch products');
//     }
//     const json = await response.json();
//     console.log('API Response Data:', json.data.content);
//     return json.data.content;
//   } catch (error) {
//     console.error('Error fetching products:', error);
//     return [];
//   }
// }

export default function MyProductPage() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const [filter, setFilter] = useState<string>('ALL');

  // const selectedCategory = 'All'; // You can modify this dynamically

  // Construct API endpoint
  // const categoryQuery = selectedCategory !== 'All' ? `&categoryName=${encodeURIComponent(selectedCategory)}` : '';
  const url = `/api/v2/product/user/filter?userId=${encodeURIComponent(userId)}&productStatus=${encodeURIComponent(filter)}`;

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