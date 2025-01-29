'use client';
import { LoginForm } from "@/components/login-form";
import React from "react";
export default function LoginPage() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-brand-mid via-brand-dark to-brand-mid">
      <div className="w-full max-w-sm md:max-w-3xl mt-10 mb-10 ">
        <LoginForm />
      </div>
    </div>
  )
}