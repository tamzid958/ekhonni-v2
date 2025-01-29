
// This code is for testing  purposes only for the understanding of dev team. It will  not be used in the final project.

"use client";

import React from 'react';
import useSWR from "swr";
import { useSession } from "next-auth/react";
import fetcher from "@/data/services/fetcher";
import Loading from '@/components/Loading';

export default function ProfilePage() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const role = session?.user?.role;

  const { data: userData, error, isLoading } = useSWR(
    userId ? `/api/v2/user/${userId}` : null,
    (url) => fetcher(url, userToken)
  );

  if (status === "loading" || isLoading) {
    return ( <div className="flex justify-center items-center h-screen"><Loading /></div>);
  }

  if (!session || error) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
        <p>You need to be signed in to view this page.</p>

      </div>
    );
  }
  const { email, profileImage, name, id, address } = userData;

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center items-center">
      <div className="bg-white shadow-xl rounded-lg p-6 w-96">
        <h1 className="text-2xl font-bold text-gray-800 mb-6 text-center">User Profile</h1>
        {profileImage && (
          <div className="flex justify-center mb-4">
            <img
              src={profileImage}
              alt={`${name}'s Profile`}
              className="w-24 h-24 rounded-full object-cover border border-gray-300"
            />
          </div>
        )}
        <div className="space-y-4">
          <div>
            <p className="text-sm text-gray-600">Name:</p>
            <p className="text-lg font-semibold text-gray-800">{name}</p>
          </div>
          <div>
            <p className="text-sm text-gray-600">Email:</p>
            <p className="text-lg font-semibold text-gray-800">{email}</p>
          </div>
          <div>
            <p className="text-sm text-gray-600">User ID:</p>
            <p className="text-lg font-semibold text-gray-800 break-all">{id}</p>
          </div>
          <div>
            <p className="text-sm text-gray-600">Address:</p>
            <p className="text-lg fill font-semibold text-gray-800">{address}</p>
          </div>
          <div>
            <p className="text-sm text-gray-600">Role:</p>
            <p className="text-lg fill font-semibold text-gray-800">{role}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
