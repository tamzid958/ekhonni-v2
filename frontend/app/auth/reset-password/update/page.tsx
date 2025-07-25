"use client";

import React, { useEffect } from 'react';

import {z} from "zod";
import { SubmitHandler, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter, useSearchParams } from 'next/navigation';
import { axiosInstance } from '@/data/services/fetcher';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';



const resetPasswordSchema = z.object({
  newPassword: z.string().min(6,"Password must be at least 6 characters"),
  confirmNewPassword: z.string().min(6,"Confirm Password must be at least 6 chracters"),
  })
  .refine((data) => data.newPassword === data.confirmNewPassword,{
    message: "Password do not match",
    path: ["confrimPassword"],
  })

  type ResetPasswordFormValues = z.infer<typeof resetPasswordSchema>;

export default function ResetPasswordPage (){
  const {
    register,
    handleSubmit,
    formState : {errors},

  } = useForm<ResetPasswordFormValues>({
    resolver: zodResolver(resetPasswordSchema),
  });

  const router = useRouter();
  const token = useSearchParams().get("token");

  if(!token)
  {
    router.push("/auth/reset-password");
  }

  const onSubmit: SubmitHandler<ResetPasswordFormValues> = async(data) => {
    try{
      const response = await axiosInstance.patch(`api/v2/auth/reset-password?token=${token}`,{
        newPassword: data.newPassword
      });
      if(response.status === 200){
        router.push(`/auth/reset-password?success=${encodeURIComponent(true)}`);
      }
      else {
        toast.error("Password Update Failed. Please Try Again Later.")
      }

    }
    catch (err)
    {
      console.error(err)
      toast.error("Something Went Wrong. Please Try Again");
    }
  }
   return (
     <div className="flex items-center justify-center min-h-screen bg-gradient-to-r bg-brand-bright">
        <Card className="w-96 max-h-fit  flex flex-col mt-10 mb-10 border-black shadow-2xl">
          <CardContent>
            <h2 className="text-lg font-bold mt-4 mb-4 text-center"> Set a new password </h2>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-3">
              <div>
                <label className="block mb-1 text-sm font-medium">New Password</label>
                <Input
                  type = "password"
                  {...register("newPassword")}
                  placeholder="Enter your new password"
                  className="w-full border-black bg-gray-100"
                />
                {errors.newPassword && (
                  <p className="text-sm text-red-600"> {errors.newPassword.message}</p>
                )}
              </div>
              <div>
                <label className="block mb-1 text-sm font-medium">Confirm Password</label>
                <Input
                  type = "password"
                  {...register("confirmNewPassword")}
                  placeholder="Confirm your new password"
                  className="w-full border-black bg-gray-100"
                />
                {errors.confirmNewPassword && (
                  <p className="text-sm text-red-600"> {errors.confirmNewPassword.message}</p>
                )}
              </div>
              <Button type="submit" className="mb-4 w-full">Update Password</Button>
            </form>
          </CardContent>

        </Card>
        
      </div>
     
   )

}