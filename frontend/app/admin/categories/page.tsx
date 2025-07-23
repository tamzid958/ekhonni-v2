'use client';

import React from 'react';
import useSWR from 'swr';
import { useSession } from 'next-auth/react';
import CategoryRender from './CategoryRender';
import Loading from '@/components/Loading';
import fetcher from '@/data/services/fetcher'; // Assuming your fetcher is here
import { useSearchParams } from 'next/navigation'; // Use for safe searchParams access

export default function Page() {
  const { data: session, status } = useSession();
  const userToken = session?.user?.token;
  const searchParams = useSearchParams(); // Safe access to searchParams
  const category = searchParams?.get('category') || 'All'; // Extract category from searchParams

  console.log(userToken);
  console.log(category);

  const url = (category === 'All') ?
    `/api/v2/admin/category/all` :
    `/api/v2/admin/category/${encodeURIComponent(category)}/subcategories`;

  // Fetch categories using SWR
  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url, userToken));

  const categories = category === 'All' ? data?.data : [data?.data] || [];
  console.log(categories);

  // Handle loading and error states
  if (status === 'loading' || isLoading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  if (error || !session) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
        <p>You need to be signed in to view this page.</p>
      </div>
    );
  }

  // Render the categories with the CategoryRender component
  return <CategoryRender category={category} categories={categories} />;
}
