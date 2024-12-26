"use client";

import { SWRConfig } from "swr";
import React from 'react';

interface FetchOptions {
  url: string;
  params?: Record<string, string | number>;
  authentication?: {
    token: string;
  };
}



const fetcher = async ({ url, params, authentication }: FetchOptions) => {
  const queryString = params
    ? `?${new URLSearchParams(params as Record<string, string>).toString()}`
    : "";
  const fullUrl = `${url}${queryString}`;

  const response = await fetch(fullUrl, {
    headers: {
      ...(authentication?.token
        ? { Authorization: `Bearer ${authentication.token}` }
        : {}),
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch: ${response.statusText}`);
  }

  return response.json();
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
        provider: () => new Map(),
        revalidateOnFocus: true,
        dedupingInterval: 2000,
        onError: (error) => {
          console.error("SWR Error:", error);
        },
      }}
    >
      {children}
    </SWRConfig>
  );
};

export default RootLayoutWrapper;
