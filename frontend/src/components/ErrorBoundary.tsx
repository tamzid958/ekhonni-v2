'use client';

import React, { FC, ReactNode } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorFallback from '@/components/ErrorFallback';

interface  CustomErrorBoundaryProps{
  children: ReactNode;
  customFallback?: ReactNode;
}

const CustomErrorBoundary:FC< CustomErrorBoundaryProps> = ({ children, customFallback }) => (
  <ErrorBoundary
    FallbackComponent={customFallback? () => <> {customFallback} </> : ErrorFallback}
    onError={(error, info) => {
      console.error('Error occurred:', error);
      console.error('Component Stack:', info.componentStack);
    }}
  >
    {children}
  </ErrorBoundary>
);

export default CustomErrorBoundary;
