'use client';

import React, {useEffect, useState}  from 'react';
import {useRouter, useSearchParams}  from 'next/navigation';
import { axiosInstance } from '@/data/services/fetcher';
import { Button } from '@/components/ui/button';

const VerifyEmail = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const token  = searchParams.get('token');

  const [status, setstatus] = useState("Verifying your email.....")

  useEffect(() => {
    const verifyToken = async () =>{
      if(!token) return;

      try{
        const response = await axiosInstance.get(`/api/v2/auth/verify-email?token=${token}`)
        if(response.status === 200)
          {
            setstatus('Email Verified Successfully');

          }
        else
          {
            setstatus('Verification Failed. The Token Maybe Invalid or expired !');

          }

      }
      catch (error)
        {
          setstatus('An error occured during emial varification');

        }

    };
    verifyToken();
  }, [token]);
  const navaigateToLogin = () =>{
    router.push('/auth/login');
  }


  return (
    <div className="flex flex-col items-center justify-center bg-white w-full min-h-screen">
      <h1 className="text-5xl text-blue-600 "> {status} </h1>
      {status === 'Email Verified Successfully' && (
        <Button onClick={navaigateToLogin}> Go to Login</Button>
      )}


    </div>

  )



};
export default VerifyEmail;
