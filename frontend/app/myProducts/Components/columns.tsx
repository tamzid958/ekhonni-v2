import { createColumnHelper } from '@tanstack/react-table';
import React from 'react';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Button } from '@/components/ui/button';
import { MoreHorizontal } from 'lucide-react';

export interface BidData {
  createdAt: string;
  status: string;
  amount: number;
  bidderId: string;
  bidderName: string;
  bidderAddress: string;
  id: number;
  currency: string;
}

async function handleApprove(bidId: number) {
  try {
    const token = 'token'; // Get token from session
    const response = await fetch(`http://localhost:8080/api/v2/bid/${bidId}/accept`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ bidId }),
    });

    if (!response.ok) {
      throw new Error('Failed to approve bid');
    }
    console.log('Bid approved successfully');
  } catch (error) {
    console.error(error);
  }
}

async function handleDelete(bidId: number) {
  try {
    const response = await fetch('/api/deleteBid', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ bidId }),
    });

    if (!response.ok) {
      throw new Error('Failed to delete bid');
    }
    console.log('Bid deleted successfully');
  } catch (error) {
    console.error(error);
  }
}


const columnHelper = createColumnHelper<BidData>();

{
  /*TODO: approve functionality fix. useSession token integrate */
}


export const columns = [
  columnHelper.accessor('bidderName', {
    header: 'Bidder Name',
  }),
  columnHelper.accessor('createdAt', {
    header: 'Created At',
    cell: (info) => new Date(info.getValue()).toLocaleString(),
  }),
  columnHelper.accessor('amount', {
    header: 'Amount',
    cell: (info) => `$${info.getValue().toFixed(2)}`,
  }),
  columnHelper.accessor('bidderAddress', {
    header: 'Bidder Address',
  }),
  columnHelper.accessor('status', {
    header: 'Status',
  }),
  columnHelper.display({
    id: 'actions',
    header: 'Actions',
    cell: (info) => {
      const bidId = info.row.original.id;
      console.log('Bid ID:', bidId);
      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <MoreHorizontal />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem onClick={() => handleApprove(bidId)}>
              Approve
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem onClick={() => handleDelete(bidId)}>
              Delete
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  }),
];
