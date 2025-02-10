import React from 'react';

export default function Page() {
  return (
    <div className="text-center">
      <h1 className="text-yellow-600 text-2xl font-bold">Payment Cancelled ⏳</h1>
      <p>You have canceled your transaction.</p>
      <a href="/public">Return Home</a>
    </div>
  );
}
