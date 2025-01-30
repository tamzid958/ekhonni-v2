import React from 'react';
import AboutPage from '@/components/AboutPage';

const UserAbout: React.FC = () => {
  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://localhost:3000`;
  const fetchUrl = `${baseUrl}/api/userProfileAbout`;

  return (

    <div className="bg-[#FAF7F0]">
      <AboutPage
        fetchUrl={fetchUrl}
        title="About Buyer"
      />
    </div>

  );
};

export default UserAbout;
