import React from 'react';
import { createColumnHelper } from '@tanstack/react-table';
import { FaArrowDown, FaArrowUp, FaExchangeAlt } from 'react-icons/fa';
import { Badge } from '@/components/ui/badge';

// Define the TransactionItem interface
export interface TransactionItem {
  createdAt: string;
  id: number;
  type: string;
  amount: number;
  currency: string;
  status: string;
  buyerName: string;
  productId: string;
  buyerId: string;
  sellerId: string;
  sellerName: string;
}

// Create column helper for the table
const columnHelper = createColumnHelper<TransactionItem>();

// Status Badge Colors
const getStatusBadge = (status: string) => {
  const statusMap: Record<string, string> = {
    Success: 'bg-green-100 text-green-700',
    Pending: 'bg-yellow-100 text-yellow-700',
    Failed: 'bg-red-100 text-red-700',
  };
  return <Badge className={`${statusMap[status] || 'bg-gray-100 text-gray-700'}`}>{status}</Badge>;
};

// Transaction Type Icon
const getTransactionIcon = (type: string) => {
  switch (type) {
    case 'SENT':
      return <FaArrowUp className="text-red-500" />;
    case 'RECEIVED':
      return <FaArrowDown className="text-green-500" />;
    case 'Exchange':
      return <FaExchangeAlt className="text-blue-500" />;
    default:
      return null;
  }
};

export function getColumns() {
  return [
    columnHelper.accessor('id', {
      header: 'Transaction ID',
    }),
    columnHelper.accessor('productId', {
      header: 'Product ID',
    }),
    columnHelper.accessor('buyerName', {
      header: 'Buyer',
    }),
    columnHelper.accessor('sellerName', {
      header: 'Seller',
    }),
    columnHelper.accessor('type', {
      header: 'Type',
      cell: (info) => (
        <div className="flex items-center space-x-2">
          {getTransactionIcon(info.getValue())}
          <span>{info.getValue()}</span>
        </div>
      ),
    }),
    columnHelper.accessor('amount', {
      header: 'Amount',
      cell: (info) => (
        <span className={`font-semibold ${info.getValue() < 0 ? 'text-red-500' : 'text-green-500'}`}>
          ${info.getValue().toFixed(2)}
        </span>
      ),
    }),
    columnHelper.accessor('currency', {
      header: 'Currency',
    }),
    columnHelper.accessor('status', {
      header: 'Status',
      cell: (info) => getStatusBadge(info.getValue()),
    }),
    columnHelper.accessor('createdAt', {
      header: 'Date',
      cell: (info) => new Date(info.getValue()).toLocaleString(),
    }),
  ];
}
