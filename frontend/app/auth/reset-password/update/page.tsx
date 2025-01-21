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



const resetPasswordSchema = z.object({
  password: z.string().min(6,"Password must be at least 6 characters"),
  confirmPassword: z.string().min(6,"Confirm Password must be at least 6 chracters"),
  })
  .refine((data) => data.password === data.confirmPassword,{
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


  const onSubmit: SubmitHandler<ResetPasswordFormValues> = async(data) => {
    try{
      const response = await axiosInstance.post(`api/v2/auth/reset-password?token=${token}`,{
        password: data.password
      });
      if(response.status === 200){
        alert("Password Has Been Successfully Updated, You Can Login Now.");
        router.push(`/auth/reset-password?success=${encodeURIComponent(true)}`);
      }
      else {
        alert("Password Update Failed. Please Try Again Later.")
      }

    }
    catch (err)
    {
      console.error(err)
      alert("Something Went Wrong. Please Try Again");
    }
  }
   return (
      <div className="flex items-cneter justify-center  h-screen bg-gray-100">
        <Card className="w-96 max-h-fit  flex flex-col mt-10 mb-10 border-black shadow-2xl">
          <CardContent>
            <h2 className="text-lg font-bold mt-4 mb-4 text-center"> Set a new password </h2>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-3">
              <div>
                <label className="block mb-1 text-sm font-medium">New Password</label>
                <Input
                  type = "password"
                  {...register("password")}
                  placeholder="Enter your new password"
                  className="w-full border-black bg-gray-100"
                />
                {errors.password && (
                  <p className="text-sm text-red-600"> {errors.password.message}</p>
                )}
              </div>
              <div>
                <label className="block mb-1 text-sm font-medium">Confirm Password</label>
                <Input
                  type = "password"
                  {...register("confirmPassword")}
                  placeholder="Confirm your new password"
                  className="w-full border-black bg-gray-100"
                />
                {errors.confirmPassword && (
                  <p className="text-sm text-red-600"> {errors.confirmPassword.message}</p>
                )}
              </div>
              <Button type="submit" className="mb-4 w-full">Update Password</Button>
            </form>
          </CardContent>

        </Card>
        
      </div>
     
   )

}