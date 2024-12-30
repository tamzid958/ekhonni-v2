'use client';

import React, { useState } from "react";
import { signIn } from "next-auth/react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { FcGoogle } from "react-icons/fc";

const signupSchema = z.object({
  firstName: z.string().min(1, "First Name is required"),
  lastName: z.string().min(1, "Last Name is required"),
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
  confirmPassword: z
    .string()
    .min(6, "Confirmation Password must be at least 6 characters"),
  phone: z
    .string()
    .min(10, "Phone number must be at least 10 digits")
    .regex(/^\d+$/, "Phone number must contain only digits"),
  address: z.string().min(1, "Address is required"),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords do not match",
  path: ["confirmPassword"],
});

const loginSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

export default function AuthForm() {
  const [isSignup, setIsSignup] = useState(false);

  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: zodResolver(isSignup ? signupSchema : loginSchema),
  });

  const onSubmit = async (data) => {
    if (isSignup) {

      try {
        const res = await fetch("/api/auth/signup", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            password: data.password,
            phone: data.phone,
            address: data.address,
          }),
        });

        const result = await res.json();
        if (res.ok) {
          alert("Signup successful! Please log in.");
          setIsSignup(false); // Switch to login form
        } else {
          alert(result.message);
        }
      } catch (err) {
        console.error("Signup error:", err);
        alert("Something went wrong. Please try again.");
      }
    } else {
      // Handle Login
      try {
        const res = await signIn("credentials", {
          redirect: false,
          email: data.email,
          password: data.password,
        });

        if (res?.ok) {
          alert("Login successful!");
          window.location.href = "/";
        } else {
          alert("Invalid email or password");
        }
      } catch (err) {
        console.error("Login error:", err);
        alert("Something went wrong. Please try again.");
      }
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-50">
      <Card className="w-96 max-h-[96vh] flex flex-col">
        <CardContent className="overflow-auto flex-grow">
          <h2 className="text-lg font-bold mb-4 mt-4  text-center">
            {isSignup ? "Sign Up" : "Log In"}
          </h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            {isSignup ? (
              <>
                <div>
                  <label className="block mb-1 text-sm font-medium">First Name</label>
                  <Input
                    type="text"
                    {...register("firstName")}
                    placeholder="Enter your first name"
                    className="w-full"
                  />
                  {errors.firstName && (
                    <p className="text-sm text-red-600">{errors.firstName.message}</p>
                  )}
                </div>
                <div>
                  <label className="block mb-1 text-sm font-medium">Last Name</label>
                  <Input
                    type="text"
                    {...register("lastName")}
                    placeholder="Enter your last name"
                    className="w-full"
                  />
                  {errors.lastName && (
                    <p className="text-sm text-red-600">{errors.lastName.message}</p>
                  )}
                </div>
                <div>
                  <label className="block mb-1 text-sm font-medium">Phone</label>
                  <Input
                    type="text"
                    {...register("phone")}
                    placeholder="Enter your phone number"
                    className="w-full"
                  />
                  {errors.phone && (
                    <p className="text-sm text-red-600">{errors.phone.message}</p>
                  )}
                </div>
                <div>
                  <label className="block mb-1 text-sm font-medium">Address</label>
                  <Input
                    type="text"
                    {...register("address")}
                    placeholder="Enter your address"
                    className="w-full"
                  />
                  {errors.address && (
                    <p className="text-sm text-red-600">{errors.address.message}</p>
                  )}
                </div>
              </>
            ) : null}
            <div>
              <label className="block mb-1 text-sm font-medium">Email</label>
              <Input
                type="email"
                {...register("email")}
                placeholder="Enter your email"
                className="w-full"
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
                className="w-full"
              />
              {errors.password && (
                <p className="text-sm text-red-600">{errors.password.message}</p>
              )}
            </div>
            {isSignup && (
              <div>
                <label className="block mb-1 text-sm font-medium">Confirm Password</label>
                <Input
                  type="password"
                  {...register("confirmPassword")}
                  placeholder="Confirm your password"
                  className="w-full"
                />
                {errors.confirmPassword && (
                  <p className="text-sm text-red-600">{errors.confirmPassword.message}</p>
                )}
              </div>
            )}
            <Button type="submit" className="w-full">
              {isSignup ? "Sign Up" : "Log In"}
            </Button>
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
        </CardContent>
        <CardFooter className="text-sm text-center">
          <p>
            {isSignup ? "Already have an account?" : "Don't have an account?"}{" "}
            <span
              className="text-blue-500 cursor-pointer"
              onClick={() => setIsSignup(!isSignup)}
            >
              {isSignup ? "Log In" : "Sign Up"}
            </span>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}
