'use client';

import React, {useState, useEffect} from 'react';
import {useSearchParams} from 'next/navigation';

const SellerPage: React.FC = () => {

  const searchParams = useSearchParams();
  const sellerId = searchParams.get("sellerId");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if(sellerId){
      setLoading(false);
    }
  }, [sellerId]);
  return <div>

  </div>;
};

export default SellerPage;
