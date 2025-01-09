'use client';
import React from 'react';

const FeedbackPage: React.FC = () => {
  const feedbackData = [
    {
      type: 'Buyer',
      name: 'Jerin Priya',
      feedback: 'Great seller! The product arrived on time and as described.',
      reply: 'Thank you for your kind words!',
      time: '2024-12-30 14:00',
      rating: 5,
    },
    {
      type: 'Seller',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! The buyer was communicative and kind.',
      reply: 'It was a pleasure working with you!',
      time: '2024-12-29 16:30',
      rating: 4,
    },
    {
      type: 'Buyer',
      name: 'Prova Sadia',
      feedback: 'Amazing product quality. Will purchase again!',
      reply: 'Glad you loved it!',
      time: '2024-12-28 12:45',
      rating: 5,
    },
    {
      type: 'Seller',
      name: 'Sadman Hafiz',
      feedback: 'The buyer was prompt in responding and quick with payment.',
      reply: 'Looking forward to more deals!',
      time: '2024-12-27 10:00',
      rating: 4,
    },
    {
      type: 'Buyer',
      name: 'Fahim Rahman',
      feedback: 'Product was okay, but the delivery was delayed.',
      reply: 'We apologize for the delay. Thank you for your patience!',
      time: '2024-12-26 18:15',
      rating: 3,
    },
    {
      type: 'Seller',
      name: 'Nusrat Jahan',
      feedback: 'The buyer asked many questions but was polite throughout.',
      reply: 'Thanks for understanding!',
      time: '2024-12-25 11:30',
      rating: 4,
    },
  ];

  const buyerFeedback = feedbackData.filter((item) => item.type === 'Buyer');
  const sellerFeedback = feedbackData.filter((item) => item.type === 'Seller');

  const renderStars = (rating: number) => {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  };

  return (

      <div className="min-h-screen bg-gray-100 p-6">
        <div className="max-w-6xl mx-auto">

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
          <div className="my-10"></div>
        </div>
      </div>

  );
};

export default FeedbackPage;
