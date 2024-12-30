import React from "react";

const FeedbackPage: React.FC = () => {
  const feedbackData = [
    {
      type: "Buyer",
      name: "John Doe",
      feedback: "Great seller! The product arrived on time and as described.",
      time: "2024-12-30 14:00",
    },
    {
      type: "Seller",
      name: "Jane Smith",
      feedback: "Smooth transaction! The buyer was communicative and kind.",
      time: "2024-12-29 16:30",
    },
    {
      type: "Seller",
      name: "Jane Smith",
      feedback: "Smooth transaction! The buyer was communicative and kind.",
      time: "2024-12-29 16:30",
    },
    {
      type: "Seller",
      name: "Jane Smith",
      feedback: "Smooth transaction! The buyer was communicative and kind.",
      time: "2024-12-29 16:30",
    },
    {
      type: "Seller",
      name: "Jane Smith",
      feedback: "Smooth transaction! The buyer was communicative and kind.",
      time: "2024-12-29 16:30",
    },
    {
      type: "Seller",
      name: "Jane Smith",
      feedback: "Smooth transaction! The buyer was communicative and kind.",
      time: "2024-12-29 16:30",
    },
  ];


  const buyerFeedback = feedbackData.filter(item => item.type === "Buyer");
  const sellerFeedback = feedbackData.filter(item => item.type === "Seller");

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-6">
        <h1 className="text-2xl font-bold text-gray-800 mb-6">User Feedback</h1>

        <div className="flex space-x-6">

          <div className="flex-1 bg-blue-50 p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">Buyer Feedback</h2>
            {buyerFeedback.map((item, index) => (
              <div key={index} className="p-4 border border-gray-200 rounded-lg bg-white mb-4">
                <p className="text-gray-600">
                  <span className="font-medium">{item.name}:</span> {item.feedback}
                </p>
                <p className="text-sm text-gray-500 mt-2">Reviewed on: {item.time}</p>
              </div>
            ))}
          </div>

          <div className="flex-1 bg-green-50 p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">Seller Feedback</h2>
            {sellerFeedback.map((item, index) => (
              <div key={index} className="p-4 border border-gray-200 rounded-lg bg-white mb-4">
                <p className="text-gray-600">
                  <span className="font-medium">{item.name}:</span> {item.feedback}
                </p>
                <p className="text-sm text-gray-500 mt-2">Reviewed on: {item.time}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedbackPage;
