"use client";

import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { signIn } from "next-auth/react";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { FcGoogle } from "react-icons/fc";
import { useRouter } from "next/navigation";

const loginSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

type LoginFormValues = z.infer<typeof loginSchema>;

export default function Login() {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit: SubmitHandler<LoginFormValues> = async (data) => {
    try {
      const result = await signIn("credentials", {
        redirect: false,
        email: data.email,
        password: data.password,
      });

      if (result?.ok) {
        alert("Login successful!");
        window.location.href = "/";
      } else {
        alert(result?.error || "Invalid email or password");
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <Card className="w-96 max-h-[96vh] flex flex-col border-black shadow-2xl">
        <CardContent>
          <h2 className="text-lg font-bold mb-4 mt-4 text-center">Log In</h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div>
              <label className="block mb-1 text-sm font-medium">Email</label>
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
            <div>
              <label className="block mb-1 text-sm font-medium">Password</label>
              <Input
                type="password"
                {...register("password")}
                placeholder="Enter your password"
                className="w-full border-black bg-gray-100"
              />
              {errors.password && (
                <p className="text-sm text-red-600">{errors.password.message}</p>
              )}
              <CardContent onClick={() => {
                router.push('/auth/forget-password')
              }} className="flex font-medium text-sm justify-end text-blue-500 mt-1 cursor-pointer">
                <p> Forget Password? </p>
              </CardContent>
            </div>
            <Button type="submit" className="w-full">Log In</Button>
          </form>
          <div className="relative my-4">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-300"></div>
            </div>
            <div className="relative flex justify-center">
              <span className="bg-white px-2 text-gray-500 text-sm">or</span>
            </div>
          </div>
          <Button
            variant="outline"
            className="mt-4 w-full flex items-center justify-center gap-2"
            onClick={() => signIn("google")}
          >
            <FcGoogle className="text-xl" />
            Sign in with Google
          </Button>

          <p className="text-sm text-center font-mono mt-4">
            Don't have an account? <span className="text-blue-500 cursor-pointer" onClick={() => router.push('/auth/register')}>Sign Up</span>
          </p>
        </CardContent>
      </Card>
    </div>
  );
}
