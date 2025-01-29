"use client";
import  React from 'react';
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import { axiosInstance } from "@/data/services/fetcher";

export default function ProfilePage() {
  const { data: session, status } = useSession();



  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [throwError, setThrowError] = useState(false);
  if (throwError) {
    throw new Error("This is a test error to trigger ErrorFallback!");
  }

  useEffect(() => {
    if (session?.user?.id && session?.user?.token) {
      const fetchUserProfile = async () => {
        try {
          const response = await axiosInstance.get(`/api/v2/user/${session.user.id}`, {
            headers: {
              Authorization: `Bearer ${session.user.token}`,
            },
          });
          setUserData(response.data);
        } catch (error) {
          console.error("Error fetching user profile:", error);
        } finally {
          setLoading(false);
        }
      };
      fetchUserProfile();
    } else {
      setLoading(false);
    }
  }, [session]);

  if (status === "loading" || loading) {
    return <div className="flex justify-center items-center h-screen">Loading...</div>;
  }

  if (!session || !userData) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
        <p>You need to be signed in to view this page.</p>
        <button
          className="bg-red-600 text-white py-2 px-6 rounded-xl shadow-lg hover:bg-red-700 hover:scale-105 transition-all"
          onClick={() => setThrowError(true)}
        >
          Trigger Error
        </button>
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
            <p className="text-lg font-semibold text-gray-800">{address}</p>
          </div>

        </div>
      </div>
    </div>
  );
}
