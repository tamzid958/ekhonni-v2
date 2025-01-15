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
    <div className="min-h-screen bg-gradient-to-br bg-gray-100 p-10 pb-20">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-2xl font-bold text-center mb-6">{title}</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-10">
          <div className="bg-white p-10 rounded-xl shadow-xl border-t-4 border-blue-500">
            <h2 className="text-2xl font-bold text-blue-400 mb-6">Buyer Feedback</h2>
            <div className="h-[30rem] overflow-y-auto space-y-8">
              {buyerFeedback.length > 0 ? (
                buyerFeedback.map((item, index) => (
                  <div
                    key={index}
                    className="p-6 border border-gray-300 rounded-lg bg-blue-50 hover:bg-blue-100 transition"
                  >
                    <p className="text-lg text-gray-700">
                      <span className="font-semibold text-blue-800">{item.name}:</span> {item.feedback}
                    </p>
                    <p className="text-sm text-gray-500 mt-3">
                      Reviewed on: {item.time}
                    </p>
                    <p className="text-yellow-500 mt-3">
                      Rating: <span className="font-semibold">{renderStars(item.rating)}</span>
                    </p>
                    {item.reply && (
                      <div className="mt-4 bg-gray-100 p-4 rounded-lg border border-gray-200">
                        <p className="text-gray-800">
                          <span className="font-semibold text-gray-900">Reply:</span> {item.reply}
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

          <div className="bg-white p-10 rounded-xl shadow-xl border-t-4 border-green-500">
            <h2 className="text-2xl font-bold text-green-400 mb-6">Seller Feedback</h2>
            <div className="h-[30rem] overflow-y-auto space-y-8">
              {sellerFeedback.length > 0 ? (
                sellerFeedback.map((item, index) => (
                  <div
                    key={index}
                    className="p-6 border border-gray-300 rounded-lg bg-green-50 hover:bg-green-100 transition"
                  >
                    <p className="text-lg text-gray-700">
                      <span className="font-semibold text-green-800">{item.name}:</span> {item.feedback}
                    </p>
                    <p className="text-sm text-gray-500 mt-3">
                      Reviewed on: {item.time}
                    </p>
                    <p className="text-yellow-500 mt-3">
                      Rating: <span className="font-semibold">{renderStars(item.rating)}</span>
                    </p>
                    {item.reply && (
                      <div className="mt-4 bg-gray-100 p-4 rounded-lg border border-gray-200">
                        <p className="text-gray-800">
                          <span className="font-semibold text-gray-900">Reply:</span> {item.reply}
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