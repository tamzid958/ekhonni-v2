'use client';

import React from 'react';
import FeedbackPage from '@/components/FeedbackPage';

const UserProfile = () => {
  const personalFeedback = [
    {
      type: 'Buyer',
      name: 'Jerin Priya',
      feedback: 'Great experience shopping here!',
      reply: 'Thank you so much!',
      time: '2024-12-20 15:00',
      rating: 5,
    },
    {
      type: 'Seller',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },

    {
      type: 'Seller',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },

    {
      type: 'Seller',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },

    {
      type: 'Seller',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },

    {
      type: 'Buyer',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },
    {
      type: 'Buyer',
      name: 'Jisan Ahmed',
      feedback: 'Smooth transaction! Thank you for purchasing.',
      reply: 'Thanks for the great service!',
      time: '2024-12-18 10:30',
      rating: 4,
    },
  ];

  return (
    <div className="bg-[#FAF7F0]">
      <FeedbackPage
        feedbackData={personalFeedback}
        title="Feedback for Personal Profile"
      />
    </div>
  );
};

export default UserProfile;
