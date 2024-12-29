"use client";

import { useSession ,signOut } from "next-auth/react";
import React from "react";

import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Button } from '@/components/ui/button';
export default function ProfilePage() {
  const { data: session, status } = useSession();

  if (status === "loading") {
    return <p>Loading...</p>;
  }
  function handleClick() {
    window.location.href = "/auth/login";
  }
  if (!session) {
    return <div className="flex flex-col items-center min-h-screen">
      <p>You are not logged in. </p>
      Please sign in to continue.
      <Button className="max-w-36" onClick={handleClick}> Go to Login page </Button>
      </div>
  }

  return (
    <div className="flex flex-col items-center mt-6 min-h-screen ">
      <Avatar className="w-24 h-24">
        <AvatarImage
          src={session.user.image || "/default-profile.png"}
          alt={session.user.name || "User"}
        />
        <AvatarFallback>{session.user.name?.charAt(0) || "?"}</AvatarFallback>
      </Avatar>

      <h1 className="text-2xl font-bold">Welcome, {session.user.name}!</h1>
      <p className="text-lg text-gray-600">Email: {session.user.email}</p>


      <Button
        className="mt-4"
        variant="destructive"
        onClick={() => signOut({
          callbackUrl: "/auth/login",
          redirect: true,
        })
        }
      >
        Sign Out
      </Button>
    </div>
  );
}
