'use client';

import React, {useEffect, useState}  from 'react';
import {useRouter, useSearchParams}  from 'next/navigation';
import { axiosInstance } from '@/data/services/fetcher';
import { Button } from '@/components/ui/button';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import Image from "next/image"

const LINK_EXPIRY_TIME = 60;

const VerifyEmail = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const token = searchParams.get('token');
  const email = searchParams.get('email');
  const [success, setSuccess] = useState(false);
  const [status, setstatus] = useState('')
  const [displayEmail, setDisplayEmail] = useState('');
  const [timeLeft, setTimeLeft] = useState(LINK_EXPIRY_TIME); // 5 minutes in seconds
  const [isExpired, setIsExpired] = useState(false);
  const [isResending, setIsResending] = useState(false);

  useEffect(() => {
    const flag = searchParams.get('isExpired');
    const success = searchParams.get('success');
    if (success) {
      setSuccess(success === 'true');
      setstatus("Your Password Has Been Reset Successfully. Please Login.");
    }
    if (flag) setIsExpired(flag === 'true');
  }, [searchParams]);


  const imageSrc = 'email-verify.png';

  useEffect(() => {

    if (email) {
      setDisplayEmail(email);
      setstatus('Please Verify Your Email Address...')
    }

  }, [email]);

  useEffect(() => {
    if (status === 'Please Verify Your Email Address...' && !isExpired) {
      const timer = setInterval(() => {
        setTimeLeft((prev) => {
          if (prev <= 1) {
            clearInterval(timer);
            setIsExpired(true);
            return 0;
          }

          return prev - 1;
        });
      }, 1000);

      return () => clearInterval(timer);
    }
  }, [status, isExpired]);


  const handleResendEmail = async () =>{
    setIsResending(true);
    try{
      const response = await axiosInstance.post('/api/v2/auth/resend-email',{email})
      if(response.status === 200)
      {
        setstatus('Verification Email Resent. Please Check Your Inbox.');
        setIsExpired(false);
        setTimeLeft(LINK_EXPIRY_TIME);

      }
      else {
        setstatus('Failed to Resend Verification Email. Please Try Again Later');
      }
    }
    catch(error){
      setstatus('An Error Occured While Resending the Email.');
    }
    finally {
      setIsResending(false);
    }
  }


  const navaigateToLogin = () => {
    router.push('/auth/login');
  }
  const formatTime = (seconds) =>
  {
    const mins = Math.floor(seconds / 60 );
    const secs = seconds % 60;
    return `${mins} Minutes and  ${secs < 10 ? '0' : '' } ${secs} Seconds`;
  }

  return (
    (email || token || success) ? (
      <div className="flex flex-col items-center bg-gray-100  min-h-screen ">

        <div className="w-[280px] h-[200px] mt-6 ">
          <AspectRatio ratio={16 / 9}>
            <Image
              src={imageSrc}
              alt="email verify"
              fill
              className="rounded-md object-contain"
            />
          </AspectRatio>
        </div>
        <div className="flex flex-col items-center  font-sans">
          <h1 className="text-5xl">{status }</h1>
          {email && !isExpired && (
            <p className="text-xl mt-2 pl-2 font-sans">
              A verification email has been sent to:<span className="text-blue-600 text-2xl"> {displayEmail} </span>. Please check your inbox and
              click on the link provided.
            </p>
          )}
        </div>
        {status === 'Please Verify Your Email Address...' && ( !isExpired ?
            (
              <p className="text-lg mt-2 text-amber-900"> Link Expires in:  {formatTime(timeLeft)}</p>
            ) : (
              <div className="flex flex-col items-center mt-4">
                <p className="text-red-600 text-xl mb-3"> The Verification Link Has Been Expired :( </p>
                <Button onClick={handleResendEmail} disabled={isResending} className="font-sans">
                  {isResending ? 'Resending' : 'Resend Email'}
                </Button>
              </div>)
        )}

        {status === 'Your Password Has Been Reset Successfully. Please Login.' && (
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
