import React from "react";

interface FeedbackProps {
  title: string;
  feedbacks: {
    id: number;
    reviewerName: string;
    revieweeName: string;
    description: string;
    rating: number;
  }[];
}

const Feedback: React.FC<FeedbackProps> = ({ title, feedbacks }) => {
  return (
    <div
      className="bg-white  shadow-md rounded-lg p-6 border-4 border-gray-50"
    >
      <h2 className="text-2xl font-semibold text-gray-900 mb-4">{title}</h2>
      {feedbacks.length > 0 ? (
        feedbacks.map((feedback) => (
          <div key={feedback.id} className="border-b border-gray-300 py-4">
            <p className="text-gray-700">
              <span className="font-medium text-gray-900">{feedback.reviewerName}:</span> {feedback.description}
            </p>
            <p className="text-yellow-500 text-sm mt-2">{"⭐".repeat(feedback.rating)}</p>
          </div>
        ))
      ) : (
        <p className="text-gray-500 text-sm">No feedback available.</p>
      )}
    </div>
  );
};

export default Feedback;
