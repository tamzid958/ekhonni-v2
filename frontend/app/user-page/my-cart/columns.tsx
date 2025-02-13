import { createColumnHelper } from '@tanstack/react-table';
import React from 'react';
import { Badge } from '@/components/ui/badge';
import { toast } from 'sonner';
import { ReviewDialog } from '@/components/Review-Dialog';

// Define the BidData interface
export interface BidList {
  amount: number;
  createdAt: string;
  status: string;
  productId: string;
  productSellerId: string;
  productTitle: string;
  productSellerName: string;
  productSellerAddress: string;
  id: number;
  currency: string;
}


// Create column helper for the table
const columnHelper = createColumnHelper<BidList>();

// This function will be used to get the columns for the table
export function getColumns(token: string) {
  const columns = [
    columnHelper.accessor('productTitle', {
      header: 'Product Title',
    }),
    columnHelper.accessor('productSellerName', {
      header: 'Seller Name',
    }),
    columnHelper.accessor('productSellerAddress', {
      header: 'Seller Address',
    }),
    columnHelper.accessor('currency', {
      header: 'Currency',
    }),
    columnHelper.accessor('amount', {
      header: 'Amount',
      cell: (info) => `${info.getValue().toFixed(2)}`,
    }),
    columnHelper.accessor('status', {
      header: 'Status',
    }),
    columnHelper.display({
      id: 'payment',
      header: () => <span>Actions</span>,
      cell: (info) => {
        const row = info.row.original;
        return (
          <div className="flex space-x-2">
            {row.status === 'ACCEPTED' && (
              <button onClick={() => handleProceedToPayment(row.id, token)}>
                <Badge className="cursor-pointer hover:bg-gray-200 hover:text-gray-700">
                  Proceed to Payment
                </Badge>
              </button>
            )}

            {row.status === 'PAID' && (
              <ReviewDialog bidId={row.id} productId={row.productId} ruleFor={'seller'}/>
            )}

          </div>
        );
      },
    }),

  ];
  return columns;
}

async function handleProceedToPayment(bidId: number, token: string) {
  const newTab = window.open('', '_blank'); // Open empty tab first (prevents popup blocking)
  try {
    const response = await fetch(`http://localhost:8080/api/v2/payment/initiate`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        bidId: bidId,
        paymentMethod: 'SSLCOMMERZ',
      }),
    });

    if (!response.ok) {
      toast('Failed to proceed with payment');
      throw new Error('Failed to proceed with payment');
    }

    const data = await response.json();
    const url = data?.data?.gatewayPageURL;
    console.log(url);
    console.log('Redirecting to:', url);
    if (url) {
      console.log('Redirecting new tab to:', url);
      newTab.location.href = url; // Redirect new tab to payment page
    } else {
      toast('Payment URL not found');
      throw new Error('Payment URL not found in response');
    }
    // toast('Payment proceeded successfully');
  } catch (error) {
    console.error('Payment Error:', error);
    toast('An error occurred while proceeding to payment');
  }
}