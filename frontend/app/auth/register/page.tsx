"use client";

import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { axiosInstance } from "@/data/services/fetcher";
import { useRouter } from "next/navigation";
import { toast } from "sonner";

const signupSchema = z
  .object({
    name: z.string().min(1, "Your Name is required"),
    email: z.string().email("Invalid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string().min(6, "Confirmation Password must be at least 6 characters"),
    phone: z
      .string()
      .min(10, "Phone number must be at least 10 digits")
      .regex(/^\d+$/, "Phone number must contain only digits"),
    address: z.string().min(1, "Address is required"),
  })
  .refine((data) => data.password === data.confirmPassword, {
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
        toast.success("SignUp Successful! Please verify your email.");
        router.push(`/auth/verify-email?email=${encodeURIComponent(data.email)}`);
      } else if (result.status === 404) {
        toast.error("Email already exists, please sign up with a different email.");
      } else if (result.status === 301) {
        toast.warning("You have already signed up. Please verify your email.");
        router.push(
          `/auth/verify-email?email=${encodeURIComponent(data.email)}&isExpired=${encodeURIComponent(false)}`
        );
      } else {
        toast.error("Signup Failed! Something went wrong.");
      }
    } catch (err: any) {
      if (err.status === 404) {
        toast.error("Email already exists, please sign up with a different email.");
      } else if (err.status === 301) {
        toast.warning("You have already signed up. Please verify your email.");
      } else {
        toast.error(err.message || "Something went wrong. Please try again.");
      }
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-brand-bright">
      <Card className="w-full max-w-lg mt-10 mb-10  p-8 bg-white shadow-2xl rounded-xl border-gray-300">
        <CardContent>
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-2">
            Create an Account
          </h2>
          <h2 className="mb-4 text-xl text-gray-500 font-mono text-center"> Join with Ekhonni.com </h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Your Name</label>
              <Input type="text" {...register("name")} placeholder="Enter your name" />
              {errors.name && <p className="text-sm text-red-600 mt-1">{errors.name.message}</p>}
            </div>
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Phone</label>
              <Input type="text" {...register("phone")} placeholder="Enter your phone number" />
              {errors.phone && <p className="text-sm text-red-600 mt-1">{errors.phone.message}</p>}
            </div>
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Address</label>
              <Input type="text" {...register("address")} placeholder="Enter your address" />
              {errors.address && <p className="text-sm text-red-600 mt-1">{errors.address.message}</p>}
            </div>
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Email</label>
              <Input type="email" {...register("email")} placeholder="Enter your email" />
              {errors.email && <p className="text-sm text-red-600 mt-1">{errors.email.message}</p>}
            </div>
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Password</label>
              <Input type="password" {...register("password")} placeholder="Enter your password" />
              {errors.password && <p className="text-sm text-red-600 mt-1">{errors.password.message}</p>}
            </div>
            <div>
              <label className="block mb-2 text-sm font-semibold text-gray-700">Confirm Password</label>
              <Input type="password" {...register("confirmPassword")} placeholder="Confirm your password" />
              {errors.confirmPassword && (
                <p className="text-sm text-red-600 mt-1">{errors.confirmPassword.message}</p>
              )}
            </div>
            <Button
              type="submit"
              className="w-full bg-black hover:bg-gray-900 text-white font-semibold py-3 rounded-lg shadow-md transition"
            >
              Sign Up
            </Button>
          </form>
        </CardContent>
        <CardFooter>
          <p className="text-center text-sm text-gray-700 mt-6">
            Already have an account?{" "}
            <span
              className="ml-auto text-sm underline underline-offset-2 cursor-pointer hover:text-blue-600 transition"
              onClick={() => router.push("/auth/login")}
            >
              Login
            </span>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}
