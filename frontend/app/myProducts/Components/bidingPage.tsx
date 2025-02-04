import React from 'react';
import { fetchBids } from './biddings';
import DataTable from './bidding-table';

export async function BidsShowPage(productId: number, token: string) {
  const data = await fetchBids(productId, token);

  return (
    <div className="bg-white">
      <DataTable data={data} />
    </div>);
}
