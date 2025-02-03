'use client';

import { useParams } from 'next/navigation';
import React, { useState, useEffect } from 'react';
import Feedback from '@/components/Feedback';

export default function SellerFeedbackPage() {
  const params = useParams();
  const userId = params.id as string; // Ensure it's a string

  const [buyerFeedback, setBuyerFeedback] = useState([]);
  const [sellerFeedback, setSellerFeedback] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const buyerResponse = await fetch(`http://localhost:8080/api/v2/review/buyer/${userId}`);
        const buyerResponseData = await buyerResponse.json();

        const sellerResponse = await fetch(`http://localhost:8080/api/v2/review/seller/${userId}`);
        const sellerResponseData = await sellerResponse.json();

        setBuyerFeedback(buyerResponseData.data.content);
        setSellerFeedback(sellerResponseData.data.content);
      } catch (error) {
        console.error("Error fetching feedback:", error);
      } finally {
        setLoading(false);
      }
    }

    if (userId) {
      fetchData();
    }
  }, [userId]);

  if (loading) {
    return <div>Loading feedback...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col items-center p-6">
      <div className="w-full max-w-6xl grid grid-cols-1 md:grid-cols-2 gap-8">
        <Feedback title="Feedback as a Buyer" feedbacks={buyerFeedback} />
        <Feedback title="Feedback as a Seller" feedbacks={sellerFeedback} />
      </div>
    </div>
  );
}
