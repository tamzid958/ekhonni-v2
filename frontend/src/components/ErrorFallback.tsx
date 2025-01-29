"use client";

import React from "react";

type ErrorFallbackProps = {
  error: Error;
  resetErrorBoundary: (...args: Array<unknown>) => void;
};

export default function ErrorFallback({ error, resetErrorBoundary }: ErrorFallbackProps) {
  console.error("Error caught by ErrorBoundary:", error);

  function handleReload() {
    resetErrorBoundary();
    window.location.reload();
  }

  return (
    <div
      role="alert"
      className="h-screen flex flex-col justify-center items-center bg-red-200 border-l-8 border-red-600 text-red-800 p-6 rounded-lg shadow-lg"
    >
      <h2 className="text-3xl font-bold mb-2">Oops! Something went wrong. 🚨</h2>
      <p className="text-md text-gray-700 text-center mb-4">
        We encountered an issue while loading this page. Please try again.
      </p>


      <details className="bg-white p-4 rounded-lg shadow-md text-sm text-gray-600 max-w-md break-words">
        <summary className="cursor-pointer font-medium text-gray-800">Error Details</summary>
        <p className="mt-2">{error.message}</p>
      </details>

      <button
        className="mt-6 bg-red-600 text-white py-2 px-6 rounded-xl shadow-lg hover:bg-red-700 hover:scale-105 transition-all"
        onClick={handleReload}
      >
        Reload Page
      </button>
    </div>
  );
}
