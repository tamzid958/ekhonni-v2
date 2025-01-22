// Register.js
"use client";

import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { axiosInstance } from '@/data/services/fetcher';
import { useRouter } from 'next/navigation';

const signupSchema = z.object({
  name: z.string().min(1, "Your Name is required"),
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
  confirmPassword: z.string().min(6, "Confirmation Password must be at least 6 characters"),
  phone: z.string().min(10, "Phone number must be at least 10 digits").regex(/^\d+$/, "Phone number must contain only digits"),
  address: z.string().min(1, "Address is required"),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords do not match",
  path: ["confirmPassword"],
});

type SignupFormValues = z.infer<typeof signupSchema>;

export default function Register() {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignupFormValues>({
    resolver: zodResolver(signupSchema),
  });

  const onSubmit: SubmitHandler<SignupFormValues> = async (data) => {
    try {
      const result = await axiosInstance.post("/api/v2/auth/sign-up", {
        name: data.name,
        email: data.email,
        password: data.password,
        phone: data.phone,
        address: data.address,
      });

      if (result.status === 200) {
        alert("SignUp Successful!! Please Verify Your Email.");
        router.push(`/auth/verify-email?email=${encodeURIComponent(data.email)}`);
      } else if (result.status === 404) {
        alert("Email already exists, please sign up with a different email.");
      } else if (result.status === 301) {
        alert("You have Signed Up Already. Please Verify Your Email.");
        router.push(`/auth/verify-email?email=${encodeURIComponent(data.email)}&isExpired=${encodeURIComponent(false)}`);
      } else {
        alert("Signup Failed!! Something went wrong!!");
      }
    } catch (err: any) {
      console.error(err);
      alert(err.message || "Signup failed.");
    }
  };


  return (
    <div className="flex items-center bg-diagonal-split justify-center  ">
      <Card className="w-96 h-full flex flex-col mt-10 mb-10 border-black shadow-2xl">
        <CardContent>
          <h2 className="text-lg font-bold mb-4 mt-4 text-center">Sign Up</h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div>
              <label className="block mb-1 text-sm font-medium">Your Name</label>
              <Input
                type="text"
                {...register("name")}
                placeholder="Enter your name"
                className="w-full border-black bg-gray-100"
              />
              {errors.name && <p className="text-sm text-red-600">{errors.name.message}</p>}
            </div>
            <div>
              <label className="block mb-1 text-sm font-medium">Phone</label>
              <Input
                type="text"
                {...register("phone")}
                placeholder="Enter your phone number"
                className="w-full border-black bg-gray-100"
              />
              {errors.phone && <p className="text-sm text-red-600">{errors.phone.message}</p>}
            </div>
            <div>
              <label className="block mb-1 text-sm font-medium">Address</label>
              <Input
                type="text"
                {...register("address")}
                placeholder="Enter your address"
                className="w-full border-black bg-gray-100"
              />
              {errors.address && <p className="text-sm text-red-600">{errors.address.message}</p>}
            </div>
            <div>
              <label className="block mb-1 text-sm font-medium">Email</label>
              <Input
                type="email"
                {...register("email")}
                placeholder="Enter your email"
                className="w-full border-black bg-gray-100"
              />
              {errors.email && <p className="text-sm text-red-600">{errors.email.message}</p>}
            </div>
            <div>
              <label className="block mb-1 text-sm font-medium">Password</label>
              <Input
                type="password"
                {...register("password")}
                placeholder="Enter your password"
                className="w-full border-black bg-gray-100"
              />
              {errors.password && <p className="text-sm text-red-600">{errors.password.message}</p>}
            </div>
            <div>
              <label className="block mb-1 text-sm font-medium">Confirm Password</label>
              <Input
                type="password"
                {...register("confirmPassword")}
                placeholder="Confirm your password"
                className="w-full border-black bg-gray-100"
              />
              {errors.confirmPassword && <p className="text-sm text-red-600">{errors.confirmPassword.message}</p>}
            </div>
            <Button type="submit" className="w-full">Sign Up</Button>

          </form>

        </CardContent>
          <p className="text-sm font-mono text-center mb-4">
            Already have an account? <span className="text-blue-500 cursor-pointer" onClick={() => router.push('/auth/login')}>Login</span>
          </p>

      </Card>
    </div>
  );
}
