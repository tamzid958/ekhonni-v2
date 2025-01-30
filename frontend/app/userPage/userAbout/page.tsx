"use client";

import React, { useEffect, useState } from "react";
import { useSession } from "next-auth/react";

export default function UserDetails() {
  const { data: session } = useSession();
  const userId = session?.user?.id;
  const token = session?.user?.token;
  const [userDetails, setUserDetails] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!userId || !token) return;

    async function fetchUserDetails() {
      try {
        const response = await fetch(`http://localhost:8080/api/v2/user/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch user details");
        }

        const data = await response.json();
        setUserDetails(data);
      } catch (err: any) {
        setError(err.message);
      }
    }

    fetchUserDetails();
  }, [userId, token]);

  if (!userId) {
    return <div className="text-center text-red-500">User Not Found</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Error: {error}</div>;
  }

  if (!userDetails) {
    return <div className="text-center text-gray-500">Loading...</div>;
  }

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
