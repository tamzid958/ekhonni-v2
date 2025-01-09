'use client';

import React from 'react';
import FeedbackPage from '@/components/FeedbackPage';

const SellerProfile = () => {
  const sellerFeedback = [
    {
      type: 'Buyer',
      name: 'Fahim Rahman',
      feedback: 'Excellent product quality!',
      reply: 'Thank you for your feedback!',
      time: '2024-11-30 13:00',
      rating: 5,
    },
    {
      type: 'Seller',
      name: 'Prova Sadia',
      feedback: 'Great customer!',
      reply: 'Looking forward to working with you again!',
      time: '2024-11-28 12:30',
      rating: 4,
    },
  ];

  return (
    <div>
      <FeedbackPage
        feedbackData={sellerFeedback}
        title="Feedback for Seller Profile"
      />
    </div>
  );
};

export default SellerProfile;
