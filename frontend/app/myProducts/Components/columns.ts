import { createColumnHelper } from '@tanstack/react-table';

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

const columnHelper = createColumnHelper<BidData>();

export const columns = [
  columnHelper.accessor('createdAt', {
    header: 'Created At',
    cell: (info) => new Date(info.getValue()).toLocaleString(),
  }),
  columnHelper.accessor('status', {
    header: 'Status',
  }),
  columnHelper.accessor('amount', {
    header: 'Amount',
    cell: (info) => `$${info.getValue().toFixed(2)}`,
  }),
  columnHelper.accessor('bidderId', {
    header: 'Bidder ID',
  }),
  columnHelper.accessor('bidderName', {
    header: 'Bidder Name',
  }),
  columnHelper.accessor('bidderAddress', {
    header: 'Bidder Address',
  }),
  columnHelper.accessor('id', {
    header: 'ID',
  }),
  columnHelper.accessor('currency', {
    header: 'Currency',
  }),
];
