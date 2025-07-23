'use client';
import { SWRConfig } from 'swr';
import React, { ReactNode } from 'react';
import fetcher from '@/data/services/fetcher';

const RootLayoutWrapper = ({ children}:{ children: ReactNode}) => {
  return (
    <SWRConfig
      value={{
        fetcher,
        refreshInterval: 3000,
        // TODO: Uncomment the following lines to disable revalidation on focus and reconnect
        // revalidateIfStale: false,
        // revalidateOnFocus: false,
        // revalidateOnReconnect: false,
        provider: () => new Map(),
        revalidateOnFocus: true,
        dedupingInterval: 2000,
      }}
    >
      {children}
    </SWRConfig>
  );
};

export default RootLayoutWrapper;
