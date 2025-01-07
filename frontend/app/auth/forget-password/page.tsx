"use client";

import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { users } from '@/lib/users';



export default function ForgetPasswordPage() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log(users);
    // Validate email against the `users` array
    const userExists = users.some((user) => user.email === email);

    if (userExists) {
      setMessage("A password reset link has been sent to your email.");
    } else {
      setMessage("No user found with this email.");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100 ">
      <div className="w-96 bg-white p-6 rounded shadow-md border-black">
        <h2 className="text-lg font-bold mb-4 text-center">Reset your password</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-1 text-sm font-medium">Email Address</label>
            <Input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              className="w-full"
              required
            />
          </div>
          <Button type="submit" className="w-full">
            Send Reset Link
          </Button>
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
      </div>
    </div>
  );
}
