'use client';

import { SWRConfig } from 'swr';
import React from 'react';

interface FetchOptions {
  url: string;
  params?: Record<string, string | number>;
  authentication?: {
    token: string;
  };
}

const fetcher = async ({ url, params, authentication }: FetchOptions) => {
  // Construct query string from params
  const queryString = params
    ? `?${new URLSearchParams(params as Record<string, string>).toString()}`
    : '';
  const fullUrl = `${url}${queryString}`;

  // Perform the fetch request
  const response = await fetch(fullUrl, {
    headers: {
      ...(authentication?.token
        ? { Authorization: `Bearer ${authentication.token}` }
        : {}),
      'Content-Type': 'application/json',
    },
  });

  // Check for non-OK response
  if (!response.ok) {
    const errorText = await response.text();
    console.error(`Fetch error: ${errorText}`);
    throw new Error(`Failed to fetch: ${response.statusText}`);
  }

  // Check the response type
  const contentType = response.headers.get('content-type');
  if (contentType && contentType.includes('application/json')) {
    return response.json();
  } else {
    throw new Error('Unexpected response format (not JSON)');
  }
};

interface RootLayoutWrapperProps {
  children: React.ReactNode;
  authentication?: { token: string };
}

const RootLayoutWrapper = ({ children, authentication }: RootLayoutWrapperProps) => {
  return (
    <SWRConfig
      value={{
        fetcher: (args: FetchOptions) =>
          fetcher({ ...args, authentication }),
        provider: () => new Map(), // Default Map provider unless needed otherwise
        revalidateOnFocus: true,
        dedupingInterval: 2000,
        onError: (error) => {
          console.error('SWR Error:', error);
        },
      }}
    >
      {children}
    </SWRConfig>
  );
};

export default RootLayoutWrapper;
