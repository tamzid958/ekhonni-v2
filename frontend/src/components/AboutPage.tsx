'use client';

import React, { useState, useEffect } from "react";

type AboutPageProps = {
  fetchUrl: string; // URL to fetch data
  title: string; // Title for the page
};

const AboutPage: React.FC<AboutPageProps> = ({ fetchUrl, title }) => {
  const [data, setData] = useState<{
    location: string;
    memberSince: string;
    profilePicture: string | null;
  } | null>(null);
  const [error, setError] = useState<string | null>(null);

  async function fetchData() {
    try {
      const response = await fetch(fetchUrl);
      if (!response.ok) {
        throw new Error("Failed to fetch data");
      }
      const data = await response.json();
      setData(data);
    } catch (err: any) {
      setError(err.message || "An unexpected error occurred");
    }
  }

  useEffect(() => {
    if (data === null && error === null) {
      fetchData();
    }
  }, [data, error]);

  return (

      <div className="p-12 max-w-4xl mx-auto min-h-screen">
        <h1 className="text-2xl font-bold text-center mb-6">{title}</h1>
        {data ? (
          <div className="mt-4 bg-white rounded p-4 shadow-lg">
            {data.profilePicture ? (
              <img
                src={data.profilePicture}
                alt="Profile Picture"
                className="w-32 h-32 rounded-full mx-auto mb-4"
              />
            ) : (
              <div className="w-32 h-32 rounded-full bg-gray-300 mx-auto mb-4" />
            )}

            <p className="text-lg text-center">
              Location: <strong>{data.location}</strong>
            </p>
            <p className="text-lg text-center">
              Member since: <strong>{data.memberSince}</strong>
            </p>
          </div>
        ) : error ? (
          <p className="text-red-500 mt-4">{error}</p>
        ) : (
          <p className="text-gray-500">Loading data...</p>
        )}
      </div>

  );
};

export default AboutPage;
