"use client";

import { useSession } from "next-auth/react";
import React from "react";
import Feedback from "@/components/Feedback";
import useSWR from "swr";
import fetcher from "@/data/services/fetcher";



export default function PersonalFeedbackPage() {
  const { data: session } = useSession();

  const userId = session?.user?.id;
  const token = session?.user?.token;
  const buyerFeedbackUrl = userId ? `http://localhost:8080/api/v2/review/buyer/${userId}` : null;
  const sellerFeedbackUrl = userId ? `http://localhost:8080/api/v2/review/seller/${userId}` : null;


  const { data: buyerFeedbackData, error: buyerError, isLoading: buyerLoading } = useSWR(
    buyerFeedbackUrl,
    (url) => fetcher(url, token || "")
  );

  const { data: sellerFeedbackData, error: sellerError, isLoading: sellerLoading } = useSWR(
    sellerFeedbackUrl,
    (url) => fetcher(url, token || "")
  );


  if (!session) {
    return <div className="text-center text-lg font-semibold text-red-600">⚠ Please log in to view your feedback.</div>;
  }

  if (buyerLoading || sellerLoading) {
    return <div className="text-center text-lg font-semibold text-blue-500">⏳ Loading feedback...</div>;
  }

  if (buyerError || sellerError) {
    return <div className="text-center text-lg font-semibold text-red-600">⚠ Error loading feedback.</div>;
  }

  return (
    <div className="min-h-screen flex flex-col items-center bg-brand-bright p-6">
      <h2 className="text-3xl font-bold text-gray-800 mb-6">Your Feedback</h2>
      <div className="w-full max-w-6xl grid grid-cols-1 md:grid-cols-2 gap-8">

        <Feedback title="As a Buyer" feedbacks={buyerFeedbackData?.data?.content || []}  />

        <Feedback title="As a Seller" feedbacks={sellerFeedbackData?.data?.content || []} />
      </div>
    </div>
  );
}
