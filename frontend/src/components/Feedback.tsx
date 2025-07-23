import React from "react";
import { format } from 'date-fns';

interface FeedbackProps {
  title: string;
  feedbacks: {
    id: number;
    reviewerName: string;
    revieweeName: string;
    description: string;
    rating: number;
    createdAt:string | number;
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
            <p className="text-gray-500 text-xs mt-1">
              {format(new Date(new Date(feedback.createdAt).getTime() + 6 * 60 * 60 * 1000), 'MMMM dd, yyyy h:mm a')}
            </p>
          </div>
        ))
      ) : (
        <p className="text-gray-500 text-sm">No feedback available.</p>
      )}
    </div>
  );
};

export default Feedback;
