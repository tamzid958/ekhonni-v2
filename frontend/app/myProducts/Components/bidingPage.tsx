import React from 'react';
import { fetchBids } from './biddings';
import DataTable from './bidding-table'; // Assuming you already have this function

export async function BidsShowPage(productId: number, token: string) {
  const data = await fetchBids(productId, token);

  return (
    <div>

      <DataTable data={data} />
    </div>);
}
