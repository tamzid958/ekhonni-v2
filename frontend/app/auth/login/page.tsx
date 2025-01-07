"use client";

import React, { useState } from "react";
import { signIn } from "next-auth/react";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { FcGoogle } from "react-icons/fc";


const signupSchema = z.object({
  name: z.string().min(1, "Your Name is required"),
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

type SignupFormValues = z.infer<typeof signupSchema>;
type LoginFormValues = z.infer<typeof loginSchema>;

export default function AuthForm() {
  const [isSignup, setIsSignup] = useState<boolean>(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignupFormValues | LoginFormValues>({
    resolver: zodResolver(isSignup ? signupSchema : loginSchema),
  });

  const onSubmit: SubmitHandler<SignupFormValues | LoginFormValues> = async (data) => {
    if (isSignup) {
      try {
        const result = await signIn("credentials", {
          redirect: false,
          email: (data as SignupFormValues).email,
          password: (data as SignupFormValues).password,
          name: (data as SignupFormValues).name,
          phone: (data as SignupFormValues).phone,
          address: (data as SignupFormValues).address,
        });

        if (result?.error) {
          console.error("Signup error:", result.error);
          alert(result.error);
        } else {
          alert("Signup successful! Please log in.");
          setIsSignup(false);
        }
      } catch (err) {
        console.error("Signup error:", err);
        alert("Something went wrong. Please try again.");
      }
    } else {
      try {
        const result = await signIn("credentials", {
          redirect: false,
          email: (data as LoginFormValues).email,
          password: (data as LoginFormValues).password,
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
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <Card className="w-96 max-h-[96vh] flex flex-col border-black shadow-card shadow-">
        <CardContent className="overflow-auto ">
          <h2 className="text-lg font-bold mb-4 mt-4 text-center">
            {isSignup ? "Sign Up" : "Log In"}
          </h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            {isSignup && (
              <>
                {/*// Signup form fields*/}
                <div>
                  <label className="block mb-1 text-sm font-medium">Your Name</label>
                  <Input
                    type="text"
                    {...register("name")}
                    placeholder="Enter your  name"
                    className="w-full"
                  />
                  {errors.name && (
                    <p className="text-sm text-red-600">{errors?.name.message}</p>
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
            )}
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

              {!isSignup &&
                (<CardContent className="flex font-medium text-sm justify-end text-blue-500 mt-1 cursor-pointer">
                 <p> Forget Password? </p>
              </CardContent>
              )}

              {errors.password && (
                <p className="text-sm text-red-600">{errors.password.message}</p>
              )}
            </div>
            {isSignup && (
              <div>
                <label className="block mb-1 text-sm font-medium">Confirm Password</label>
                <Input
                  type="password"
                  {...register('confirmPassword')}
                  placeholder="Confirm your password"
                  className="w-full"
                />

                {errors?.confirmPassword && (
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