import React from 'react';

type AboutPageProps = {
  fetchUrl: string;
  title: string;
};

type ProfileData = {
  location: string;
  memberSince: string;
  profilePicture: string | null;
};

const AboutPage = async ({ fetchUrl, title }: AboutPageProps) => {
  let data: ProfileData | null = null;
  let error: string | null = null;

  try {
    const response = await fetch(fetchUrl, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    data = await response.json();
  } catch (err: any) {
    error = err.message || 'An unexpected error occurred';
  }

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
