import React from 'react';
import { fetchBids } from './biddings';
import DataTable from './bidding-table';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';

const BidsShowPage = (productId: number) => {
  const { data: session } = useSession();
  const token = session?.user?.token;

  // Construct the URL for the API request
  const url = token ? `/api/v2/bid/seller/product/${productId}` : null;

  // Fetch bids using SWR
  const { data, error, isLoading } = useSWR(
    url,
    (url) => fetchBids(productId, token as string), // Pass token to fetchBids function
    {
      revalidateOnFocus: false, // Optional, adjust based on your needs
    },
  );

  // Handle loading state
  if (isLoading) {
    return <div>Loading...</div>;
  }

  // Handle error state
  if (error) {
    return <div>Error loading bids. Please try again later.</div>;
  }

  // Handle no data state
  if (!data || data.length === 0) {
    return <div>No bids available for this product.</div>;
  }

  return (
    <div className="bg-white">
      <DataTable data={data} />
    </div>
  );
};

export default BidsShowPage;
