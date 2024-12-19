import React from 'react';
import { HorizontalCard } from '@/components/HorizontalCard';

export default function Home() {
  return (
    <div className="flex mt-6 justify-center align-middle min-h-screen">
      {/* Render each card using map */}
      {/*<div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">*/}
      {/*  {dataDemo.map((item, index) => (*/}
      {/*    <CardDemo key={index} {...item} />*/}
      {/*  ))}*/}
      {/*</div>*/}
      <HorizontalCard />
    </div>
  );
}
