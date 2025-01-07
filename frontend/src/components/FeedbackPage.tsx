'use client';
import React from 'react';

interface Feedback {
  type: string;
  name: string;
  feedback: string;
  reply: string;
  time: string;
  rating: number;
}

interface FeedbackPageProps {
  feedbackData: Feedback[];
  title: string;
}

const FeedbackPage: React.FC<FeedbackPageProps> = ({ feedbackData, title }) => {
  const buyerFeedback = feedbackData.filter((item) => item.type === 'Buyer');
  const sellerFeedback = feedbackData.filter((item) => item.type === 'Seller');

  const renderStars = (rating: number) => {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">{title}</h1>
        </div>

        <div className="flex flex-col md:flex-row gap-6">
          <div className="flex-1 bg-blue-50 p-6 rounded-lg shadow-md">
            <h2 className="text-2xl font-semibold text-gray-700 mb-4">
              Buyer Feedback
            </h2>
            <div className="h-96 overflow-y-auto">
              {buyerFeedback.length > 0 ? (
                buyerFeedback.map((item, index) => (
                  <div
                    key={index}
                    className="p-4 border border-gray-200 rounded-lg bg-white mb-4"
                  >
                    <p className="text-gray-600">
                      <span className="font-medium">{item.name}:</span>{' '}
                      {item.feedback}
                    </p>
                    <p className="text-sm text-gray-500 mt-2">
                      Reviewed on: {item.time}
                    </p>
                    <p className="text-yellow-500 mt-2">
                      Rating: <span className="font-semibold">{renderStars(item.rating)}</span>
                    </p>
                    {item.reply && (
                      <div className="mt-2 bg-gray-100 p-3 rounded-lg border">
                        <p className="text-gray-700">
                          <span className="font-medium">Reply:</span> {item.reply}
                        </p>
                      </div>
                    )}
                  </div>
                ))
              ) : (
                <p className="text-gray-500">No buyer feedback available.</p>
              )}
            </div>
          </div>

          <div className="flex-1 bg-green-50 p-6 rounded-lg shadow-md">
            <h2 className="text-2xl font-semibold text-gray-700 mb-4">
              Seller Feedback
            </h2>
            <div className="h-96 overflow-y-auto">
              {sellerFeedback.length > 0 ? (
                sellerFeedback.map((item, index) => (
                  <div
                    key={index}
                    className="p-4 border border-gray-200 rounded-lg bg-white mb-4"
                  >
                    <p className="text-gray-600">
                      <span className="font-medium">{item.name}:</span>{' '}
                      {item.feedback}
                    </p>
                    <p className="text-sm text-gray-500 mt-2">
                      Reviewed on: {item.time}
                    </p>
                    <p className="text-yellow-500 mt-2">
                      Rating: <span className="font-semibold">{renderStars(item.rating)}</span>
                    </p>
                    {item.reply && (
                      <div className="mt-2 bg-gray-100 p-3 rounded-lg border">
                        <p className="text-gray-700">
                          <span className="font-medium">Reply:</span> {item.reply}
                        </p>
                      </div>
                    )}
                  </div>
                ))
              ) : (
                <p className="text-gray-500">No seller feedback available.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedbackPage;
