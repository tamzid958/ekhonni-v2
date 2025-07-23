'use client';

import React from 'react';
import { usePathname } from 'next/navigation';
import { NavBar } from '@/components/NavBar';
import { TopCAtegory } from '@/components/TopCategory';
import Footer from '@/components/Footer';

export const ConditionalNavBar = () => {
  const pathname = usePathname();

  const isAuthOrAdmin = pathname.startsWith('/auth') || pathname.startsWith('/admin');

  return (
    <>
      { !isAuthOrAdmin ? (
        <>
          <NavBar placeholder="What are you looking for?" />
          <TopCAtegory />
        </>
      ) : null}
    </>
  );
};
export const ConditionalFooter = () =>{
  const pathname = usePathname();
  const isAuthOrAdmin = pathname.startsWith('/auth') || pathname.startsWith('/admin');
  return (
    <>
      { !isAuthOrAdmin ? (
        <>
          <Footer />
        </>
      ) : null}
    </>
  );
}
