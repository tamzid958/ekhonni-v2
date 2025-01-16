'use client';

import React, {useEffect, useState}  from 'react';
import {useRouter, useSearchParams}  from 'next/navigation';
import { axiosInstance } from '@/data/services/fetcher';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import Image from "next/image"


const VerifyEmail = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const token = searchParams.get('token');
  const email = searchParams.get('email');
  const [status, setstatus] = useState('')
  const [displayEmail, setDisplayEmail] = useState('');

  const imageSrc = status === 'Email Verified Successfully! Please login.' ? '/check.png' : '/email-verify.png';

  useEffect(() => {

    if (email) {
      setDisplayEmail(email);
      setstatus('Please Verify Your Email Address...')
    } else if (token) {
      setstatus('Verifying Email....');
      const verifyToken = async () => {
        if (!token) return;
        try {
          const response = await axiosInstance.get(`/api/v2/auth/verify-email?token=${token}`)
          if (response.status === 200) {
            setstatus('Email Verified Successfully! Please login.');
          } else {
            setstatus('Verification Failed. The Token Maybe Invalid or expired !');
          }
        } catch (error) {
          setstatus('An Error Occurred During Email verification');

        }

      };
      verifyToken();
    }
  }, [email, token]);

  const navaigateToLogin = () => {
    router.push('/auth/login');
  }

  return (
    (email || token) ? (
      <div className="flex flex-col items-center bg-white  min-h-screen ">

        <div className="w-[280px] h-[200px] mt-6 ">
          <AspectRatio ratio={16 / 9}>
            <Image
              src={imageSrc}
              alt="Image"
              fill
              className="rounded-md object-cover"
            />
          </AspectRatio>
        </div>
        <div className="flex flex-col items-center  font-sans">
          <h1 className="text-5xl">{status }</h1>
          {email && (
            <p className="text-xl mt-2 pl-2 font-sans">
              A verification email has been sent to:<span className="text-blue-600 text-2xl"> {displayEmail} </span>. Please check your inbox and
              click on the link provided.
            </p>
          )}
        </div>
        {status === 'Email Verified Successfully! Please login.' && (
          <Button onClick={navaigateToLogin} className="mt-5 font-sans">
            Go to Login
          </Button>
        )}
      </div>

    ) : (
      <div className="flex flex-col items-center min-h-screen mt-20 p-10 text-5xl font-sans">
        <p> Please Sign Up with an email first :( </p>

        <p> Oi Miya!! Sign Up Na koira kiser Verify korben? Age Signup kore ashen, Jan! :3)</p>
      </div>
    )
  );
}
export default VerifyEmail;
