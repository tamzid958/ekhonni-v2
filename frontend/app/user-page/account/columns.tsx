import { createColumnHelper } from '@tanstack/react-table';

// Define the BidData interface
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

// This function will be used to get the columns for the table
export function getColumns() {
  const columns = [
    columnHelper.accessor('id', {
      header: 'Transaction ID',
    }),
    columnHelper.accessor('productId', {
      header: 'Product ID',
    }),
    columnHelper.accessor('buyerName', {
      header: 'Buyer Name',
    }),
    columnHelper.accessor('sellerName', {
      header: 'Seller Name',
    }),
    columnHelper.accessor('type', {
      header: 'Transaction Type',
    }),
    columnHelper.accessor('amount', {
      header: 'Amount',
      cell: (info) => `$${info.getValue().toFixed(2)}`, // formatting amount
    }),
    columnHelper.accessor('currency', {
      header: 'Currency',
    }),
    columnHelper.accessor('status', {
      header: 'Status',
    }),
    columnHelper.accessor('createdAt', {
      header: 'Transaction Time',
      cell: (info) => new Date(info.getValue()).toLocaleString(), // formatting date
    }),
    // columnHelper.accessor('buyerId', {
    //   header: 'Buyer ID',
    // }),
    // columnHelper.accessor('sellerId', {
    //   header: 'Seller ID',
    // }),
  ];

  return columns;
}
