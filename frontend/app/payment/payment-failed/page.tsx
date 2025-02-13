import React from 'react';

export default function Page() {
  return (
    <div className="text-center">
      <h1 className="text-red-600 text-2xl font-bold">Payment Failed ❌</h1>
      <p>There was an issue with your transaction. Please try again.</p>
      <a href="/public">Return Home</a>
    </div>
  );
}
