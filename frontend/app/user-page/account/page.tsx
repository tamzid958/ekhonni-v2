'use client';

import React from 'react';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import DataTable from './transaction-table';
import { useUsers } from '../../admin/hooks/useUser';
import PaginationComponent from '@/components/pagination/Pagination';
import { useRouter, useSearchParams } from 'next/navigation';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

export default function MyAccountPage() {
  const { data: session } = useSession();
  const userToken = session?.user?.token;
  const userId = session?.user?.id;
  const { getUserById, isLoading: userLoading, allUserError } = useUsers(userId, userToken);
  const user = getUserById(userId);
  const searchParams = useSearchParams();
  const router = useRouter();

  const currentPage = Number(searchParams.get('page')) || 1;
  const size = Number(searchParams.get('size')) || 20;
  const sortBy = searchParams.get('sortBy') || 'processedAt';
  const sortDirection = searchParams.get('sortDirection') || 'desc';
  const apiPage = currentPage - 1;  // Correctly adjust for the API

  const sizeOptions = [2, 5, 10, 15, 20];
  const sortOptions = [
    { value: 'processedAt', label: 'Newest First', direction: 'desc' },
    { value: 'processedAt', label: 'Oldest First', direction: 'asc' },
    { value: 'amount', label: 'Highest Amount', direction: 'desc' },
    { value: 'amount', label: 'Lowest Amount', direction: 'asc' },
    { value: 'sellerName', label: 'Seller Name A-Z', direction: 'asc' },
    { value: 'sellerName', label: 'Seller Name Z-A', direction: 'desc' },
    { value: 'createdAt', label: 'Newest Created', direction: 'desc' },
    { value: 'createdAt', label: 'Oldest Created', direction: 'asc' },
  ];

  // Constructing params dynamically for API call
  const params = new URLSearchParams();
  params.set('page', apiPage.toString());  // Use apiPage instead of currentPage
  params.set('size', size.toString());
  params.set('sortBy', sortBy);  // Change here: instead of 'sort', use 'sortBy'
  params.set('sortDirection', sortDirection);  // Change here: instead of 'sort', use 'sortDirection'

  const url = `/api/v2/transaction/user?${params.toString()}`;
  const balanceUrl = `/api/v2/account/user/balance`;

  console.log('url: ' + url);

  const { data, error, isLoading: transactionLoading } = useSWR(
    userToken ? [url, userToken] : null,
    ([url, token]) => fetcher(url, token),
  );

  const { data: balanceData, error: balanceError, isLoading: balanceLoading } = useSWR(
    userToken ? [balanceUrl, userToken] : null,
    ([url, token]) => fetcher(url, token),
  );

  const transactions = data?.data?.content || [];
  const totalPages = data?.data?.page?.totalPages;
  const totalElement = data?.data?.page?.totalElements;
  const balance = balanceData?.data ?? 'Loading...';

  const handleSizeChange = (newSize: number) => {
    const updatedParams = new URLSearchParams(searchParams.toString());
    updatedParams.set('size', newSize.toString());
    updatedParams.set('page', '1');  // Reset to first page when changing size
    router.push(`?${updatedParams.toString()}`);
  };

  const handleSortChange = (selectedLabel: string) => {
    const selectedOption = sortOptions.find(option => option.label === selectedLabel);
    if (!selectedOption) return;

    const updatedParams = new URLSearchParams(searchParams.toString());
    updatedParams.set('sortBy', selectedOption.value);
    updatedParams.set('sortDirection', selectedOption.direction);
    updatedParams.set('page', '1');  // Reset to first page when changing sort
    router.push(`?${updatedParams.toString()}`);
  };

  if (userLoading || transactionLoading || balanceLoading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  if (error || allUserError || balanceError) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Error loading data</h1>
        <p>Please try again later.</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-6 space-y-6 h-screen bg-gray-100 rounded-lg shadow-md">
      <div className="bg-white p-6 rounded-lg shadow-lg">
        <h1 className="text-2xl font-bold text-gray-700 mb-4">Account Overview</h1>
        <div className="flex justify-between">
          <div className="flex items-center space-x-4">
            <img
              src={user?.profileImage || '/default-profile.png'}
              alt="User Profile"
              className="w-24 h-24 rounded-full object-cover border-2 border-gray-300"
            />
            <div>
              <h2 className="text-xl font-semibold">{user?.name}</h2>
              <p className="text-gray-600">{user?.email}</p>
              <p className="text-gray-700 font-medium">Role: {user?.roleName}</p>
            </div>
          </div>
          <div className="text-right">
            <p className="text-gray-500">Account Created: {new Date(user?.createdAt).toLocaleDateString()}</p>
            <p className="text-2xl font-bold text-green-600">Balance: {balance} USD</p>
          </div>
        </div>
      </div>

      <div className="bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold text-gray-700 mb-4">Transaction History</h2>

        {/* Filter and Sort */}
        <div className="flex justify-end items-center mb-4">
          <Select value={String(size)} onValueChange={(val) => handleSizeChange(Number(val))}>
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Items per page" />
            </SelectTrigger>
            <SelectContent>
              {sizeOptions.map((option) => (
                <SelectItem key={option} value={String(option)}>
                  {option} items per page
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Select
            value={sortOptions.find(opt => opt.value === sortBy && opt.direction === sortDirection)?.label}
            onValueChange={handleSortChange}
          >
            <SelectTrigger className="border p-2 m-2 rounded w-auto">
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              {sortOptions.map((option) => (
                <SelectItem key={`${option.value},${option.direction}`} value={option.label}>
                  {option.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <DataTable data={transactions} />
        <div className="p-2 m-2">
          <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
        </div>
      </div>
    </div>
  );
}
