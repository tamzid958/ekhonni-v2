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
import { toast } from 'sonner';
import Link from 'next/link';

// Define the BidData interface
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

// Function to handle the approve action
async function handleApprove(bidId: number, token: string) {
  try {
    const response = await fetch(`http://localhost:8080/api/v2/bid/${bidId}/accept`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ bidId }),
    });

    if (!response.ok) {
      toast('Failed to approve bid');
      throw new Error('Failed to approve bid');
    }
    toast('Bid accepted successfully');
  } catch (error) {
    console.error(error);
  }
}

// Create column helper for the table
const columnHelper = createColumnHelper<BidData>();

// This function will be used to get the columns for the table
export function getColumns(productStatus: string, token: string) {
  const columns = [
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
  ];

  columns.push(
    columnHelper.display({
      id: 'actions',
      header: 'Actions',
      cell: (info) => {
        const bidId = info.row.original.id;
        const status = info.row.original.status;

        return (
          <div className="flex space-x-2">
            {/* Show dropdown only if product is not SOLD */}
            {productStatus !== 'SOLD' && (
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" className="h-8 w-8 p-0">
                    <MoreHorizontal />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end">
                  <DropdownMenuLabel>Actions</DropdownMenuLabel>
                  <DropdownMenuItem onClick={() => handleApprove(bidId, token)}>
                    Approve
                  </DropdownMenuItem>
                  <DropdownMenuSeparator />
                </DropdownMenuContent>
              </DropdownMenu>
            )}

            {/* Show "Leave a Review" button if status is PAID */}
            {status === 'PAID' && (
              <Link href={`/leave-review?bidId=${bidId}`}>
                <Button variant="outline">
                  Leave a Review
                </Button>
              </Link>
            )}
          </div>
        );
      },
    }),
  );


  return columns;
}
