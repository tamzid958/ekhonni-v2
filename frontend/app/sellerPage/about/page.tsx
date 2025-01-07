import AboutPage from '@/components/AboutPage';
import React from 'react';

const SellerAbout = () => {
  return (
    <AboutPage
      fetchUrl="/api/userProfileAbout"
      title="About the Seller"
    />
  );
};

export default SellerAbout;
