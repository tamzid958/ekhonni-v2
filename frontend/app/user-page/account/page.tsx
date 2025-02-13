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

export default function MyAccountPage() {
  const { data: session } = useSession();
  const userToken = session?.user?.token;
  const userId = session?.user?.id;
  const { getUserById, isLoading: userLoading, allUserError } = useUsers(userId, userToken);
  const user = getUserById(userId);
  const searchParams = useSearchParams();
  const router = useRouter();

  const currentPage = parseInt(searchParams.get('page') || '1', 10);
  const apiPage = currentPage - 1;


  const transactionUrl = `/api/v2/transaction/user?page=${apiPage}&size=20`;
  const balanceUrl = `/api/v2/account/user/balance`;

  const { data, error, isLoading: transactionLoading } = useSWR(
    userToken ? [transactionUrl, userToken] : null,
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

  console.log('total pages ' + totalPages);
  console.log('total ' + totalElement);

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
        <DataTable data={transactions} />
        <div className="p-2 m-2">
          <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
        </div>
      </div>
    </div>
  );
}
