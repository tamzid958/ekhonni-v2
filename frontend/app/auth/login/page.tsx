'use client';
import { LoginForm } from "@/components/login-form";
import React from "react";
export default function LoginPage() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-brand-bright">
      <div className="w-full max-w-sm md:max-w-3xl mt-10 mb-10 ">
        <LoginForm />
      </div>
    </div>
  )
}
