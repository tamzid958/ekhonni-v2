'use client';
import { signOut } from 'next-auth/react';
import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';

const LogoutPage = () => {
  const router = useRouter();

  useEffect(() => {
    const handleLogout = async () => {
      await signOut({ callbackUrl: '/' });
      // Redirect after signing out
      router.push('/');
    };

    handleLogout();
  }, [router]);

  return (
    <div>
      <p>Logging out...</p>
    </div>
  );
};

export default LogoutPage;
