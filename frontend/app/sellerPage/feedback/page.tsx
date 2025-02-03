import Feedback from "@/components/Feedback";
import React from "react";

// Function to fetch feedbacks for a seller and buyer based on userId
async function fetchSellerFeedbacks() {
  // Fetching buyer feedback for the given userId
  const buyerResponse = await fetch(`http://localhost:8080/api/v2/review/buyer/550e8400-e29b-41d4-a716-446655440005`);
  const buyerResponseData = await buyerResponse.json();

  // Fetching seller feedback for the given userId
  const sellerResponse = await fetch(`http://localhost:8080/api/v2/review/seller/550e8400-e29b-41d4-a716-446655440005`);
  const sellerResponseData = await sellerResponse.json();

  // Return the feedbacks for buyer and seller
  return {
    buyerFeedback: buyerResponseData.data.content,
    sellerFeedback: sellerResponseData.data.content,
  };
}

// SellerFeedbackPage component
export default async function SellerFeedbackPage() {


  // Fetching feedbacks for the seller based on the userId
  const { buyerFeedback, sellerFeedback } = await fetchSellerFeedbacks();

  return (
    <div className="p-6">
      {/* Display feedback for the seller */}
      <Feedback title="Feedback as a Buyer" feedbacks={buyerFeedback} />
      <Feedback title="Feedback as a Seller" feedbacks={sellerFeedback} />
    </div>
  );
}
