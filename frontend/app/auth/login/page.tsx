'use client';
import { LoginForm } from "@/components/login-form";
import React from "react";
export default function LoginPage() {
  return (
    <div className="flex min-h-svh bg-diagonal-split items-center justify-center bg-muted p-6  md:p-10">
      <div className="w-full max-w-sm md:max-w-3xl ">
        <LoginForm />
      </div>
    </div>
  )
}