'use client'

import React from 'react';
import { ErrorBoundary } from 'react-error-boundary';

const ErrorFallback = ({ error, resetErrorBoundary }) => {
  console.error('Error caught by ErrorBoundary:', error);

  function handleReload() {
    resetErrorBoundary();
    //window.location.reload();
  }

  return (
    <div role="alert"
         className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded-lg shadow-md flex flex-col items-center">
      <h2 className="text-xl font-semibold mb-2">Something went wrong: <span
        className="font-medium">{error.message}</span></h2>
      <p className="text-sm mb-4">Please try reloading the page to resolve the issue.</p>
      <button className="bg-blue-500 text-white py-2 px-4 rounded-xl shadow-lg hover:bg-blue-600 transition-all"
              onClick={handleReload}>
        Reload the page
      </button>
    </div>

  );
};


const CustomErrorBoundary = ({ children }) => (
  <ErrorBoundary
    FallbackComponent={ErrorFallback}
    onError={(error, info) => {
      console.error('Error occurred:', error, info);
    }}
  >
    {children}
  </ErrorBoundary>
);

export default CustomErrorBoundary;
