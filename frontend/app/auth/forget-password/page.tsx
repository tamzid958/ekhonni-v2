"use client";

import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, SubmitHandler } from "react-hook-form";
import { z } from "zod";
import { axiosInstance } from "@/data/services/fetcher";
import { useRouter } from "next/navigation";

const forgetPasswordSchema = z.object({
  email: z.string().email("Invalid email address"),
});

type ForgetPasswordFormValues = z.infer<typeof forgetPasswordSchema>;

export default function ForgetPasswordPage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ForgetPasswordFormValues>({
    resolver: zodResolver(forgetPasswordSchema),
  });

  const [message, setMessage] = useState("");
  const router = useRouter();

  const onSubmit: SubmitHandler<ForgetPasswordFormValues> = async (data) => {
    try {
      const response = await axiosInstance.post("/api/v2/auth/password-reset-request", {
        email: data.email,
      });

      if (response.status === 200) {
        setMessage("A password reset link has been sent to your email. Please verify.");
        router.push(`/auth/reset-password?email=${encodeURIComponent(data.email)}&isExpired=${encodeURIComponent(false)}`);
      } else {
        setMessage("Failed to send reset link. Please try again later.");
      }
    } catch (err) {
      console.error(err);
      setMessage("Something Went Wrong, Please Try Again Later");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <Card className="w-96 max-h-[96vh] flex flex-col border-black shadow-2xl">
        <CardContent>
          <h2 className="text-lg font-bold mb-4 mt-4 text-center">Reset your password</h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div>
              <label className="block mb-1 text-sm font-medium">Email Address</label>
              <Input
                type="email"
                {...register("email")}
                placeholder="Enter your email"
                className="w-full border-black bg-gray-100"
              />
              {errors.email && (
                <p className="text-sm text-red-600">{errors.email.message}</p>
              )}
            </div>
            <Button type="submit" className="w-full mb-4">Send Reset Link</Button>
          </form>
          {message && (
            <p
              className={`text-center mt-4 ${
                message.includes("sent") ? "text-green-600" : "text-red-600"
              }`}
            >
              {message}
            </p>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
