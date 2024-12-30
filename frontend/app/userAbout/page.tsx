'use client';

import React, { useState, useEffect } from "react";

const UserAboutPage = () => {
  const [user, setUser] = useState<{ location: string; memberSince: string } | null>(null);
  const [error, setError] = useState<string | null>(null);

  async function fetchUserData() {
    try {
      const response = await fetch("/api/userProfileAbout");
      if (!response.ok) {
        throw new Error("Failed to fetch user data");
      }
      const data = await response.json();
      setUser(data);
    } catch (err: any) {
      setError(err.message || "An unexpected error occurred");
    }
  }

  useEffect(() => {
    if (user === null && error === null) {
      fetchUserData();
    }
  }, [user, error]);

  return (
    <div className="p-12 max-w-4xl mx-auto min-h-screen">
      {user ? (
        <div className="mt-4 rounded p-4 shadow-lg">
          <p className="text-lg text-center">
           Location: <strong>{user.location}</strong>
        </p>
          <p className="text-lg text-center">
            Member since: <strong>{user.memberSince}</strong>
        </p>
        </div>
      ) : error ? (
        <p className="text-red-500 mt-4">{error}</p>
      ) : (
        <p className="text-gray-500">Loading user data...</p>
      )}
    </div>
  );
}

export default UserAboutPage;
