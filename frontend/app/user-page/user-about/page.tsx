"use client";

import React from "react";
import { useSession } from "next-auth/react";
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';


interface UserDetails {
  profileImage: string | null;
  email: string;
  name: string;
  id: string;
  address: string;
}


export default function UserDetails() {
  const { data: session } = useSession();
  const userId = session?.user?.id;
  const token = session?.user?.token;

  const url = userId ? `http://localhost:8080/api/v2/user/${userId}` : null;

  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url, token));

  if (!userId) {
    return <div className="text-center text-red-500">User Not Found</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Error: {error}</div>;
  }

  if (isLoading || !data) {
    return <div className="text-center text-gray-500">Loading...</div>;
  }
  const userDetails: UserDetails = data.data;



  return (
    <div className="w-full bg-brand-bright">
      <div className="p-12 max-w-4xl mx-auto min-h-screen ">
        <h1 className="text-2xl font-bold text-center mb-6">My Profile</h1>
        <div className="mt-4 bg-white rounded p-6 shadow-lg text-center">
          {userDetails.profileImage ? (
            <img
              src={userDetails.profileImage}
              alt="Profile"
              className="w-32 h-32 rounded-full mx-auto mb-4"
            />
          ) : (
            <div className="w-32 h-32 rounded-full bg-gray-300 mx-auto mb-4" />
          )}
          <p className="text-lg">
            <strong>Name:</strong> {userDetails.name}
          </p>
          <p className="text-lg">
            <strong>Email:</strong> {userDetails.email}
          </p>
          <p className="text-lg">
            <strong>Address:</strong> {userDetails.address}
          </p>
        </div>
      </div>
    </div>

  );
}
