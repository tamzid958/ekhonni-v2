"use client";

import { useSession } from "next-auth/react";
import React, { useEffect, useState } from "react";
import Feedback from "@/components/Feedback"; // Adjust the path if necessary

export default function PersonalFeedbackPage() {
  const { data: session } = useSession();
  const [buyerFeedbacks, setBuyerFeedbacks] = useState([]);
  const [sellerFeedbacks, setSellerFeedbacks] = useState([]);
  const [loading, setLoading] = useState(true);

  const userId = session?.user?.id;
  const token = session?.user?.token;

  useEffect(() => {
    if (!userId || !token) {
      setLoading(false);
      return;
    }

    const fetchFeedback = async (type: "buyer" | "seller") => {
      try {
        const response = await fetch(`http://localhost:8080/api/v2/review/${type}/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const data = await response.json();
        if (data.success && data.data?.content) {
          if (type === "buyer") setBuyerFeedbacks(data.data.content);
          else setSellerFeedbacks(data.data.content);
        }
      } catch (error) {
        console.error(`Error fetching ${type} feedback:`, error);
      }
    };

    Promise.all([fetchFeedback("buyer"), fetchFeedback("seller")]).finally(() => setLoading(false));
  }, [userId, token]);

  if (!session) {
    return <div className="text-center text-lg font-semibold text-red-600">⚠ Please log in to view your feedback.</div>;
  }

  if (loading) {
    return <div className="text-center text-lg font-semibold text-blue-500">⏳ Loading feedback...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col items-center bg-brand-bright p-6">
      <h2 className="text-3xl font-bold text-gray-800 mb-6">Your Feedback</h2>
      <div className="w-full max-w-6xl grid grid-cols-1 md:grid-cols-2 gap-8">

        <Feedback title="As a Buyer" feedbacks={buyerFeedbacks}  />

        <Feedback title="As a Seller" feedbacks={sellerFeedbacks} />
      </div>
    </div>
  );
}
