"use client";
import React from 'react';
import { useParams } from "next/navigation";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import UserDetails from "../../components/userDetails";
import { useSession } from 'next-auth/react';
import { useUsers } from '../../hooks/useUser';
import Loading from '@/components/Loading';

export default function UserDetailsPage() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  const { id } = useParams();
  const { getUserById, isLoading, allUserError } = useUsers(userId, userToken);

  const user = getUserById(Array.isArray(id) ? id[0] : id);

  console.log("Retrieved User", user);

  if (!id) return <p className="text-red-500">User ID not found.</p>;
  if (!user) return <div className="text-center text-gray-600">User not found.</div>;

  if (status === "loading" || isLoading) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <Loading />
      </div>
    );
  }

  if (!session) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <div className="text-center bg-white p-6 md:p-8 rounded-lg shadow-lg">
          <h1 className="text-xl md:text-2xl font-bold mb-4">Access Denied</h1>
          <p className="text-sm md:text-base">You need to be signed in to view this page.</p>
        </div>
      </div>
    );
  }

  if (allUserError) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <div className="text-center bg-white p-6 md:p-8 rounded-lg shadow-lg">
          <h1 className="text-xl md:text-2xl font-bold mb-4">Error</h1>
          <p className="text-sm md:text-base">Failed to Load User Data</p>
        </div>
      </div>
    );
  }
  return (
    <div className="flex justify-center items-center h-screen w-screen p-4">
      <div className="w-full sm:max-w-md bg-white rounded-lg p-4 md:p-6 ">

        <Card className="shadow-lg border rounded-lg mb-2">
          <CardHeader className="flex flex-col sm:flex-row items-center space-x-0 sm:space-x-4 text-center sm:text-left">
            <Avatar className="h-16 w-16">
              <AvatarImage src={user.profileImage} alt={user.name} />
              <AvatarFallback>{user.name.charAt(0)}</AvatarFallback>
            </Avatar>
            <div>
              <CardTitle className="text-lg md:text-xl font-bold">{user.name}</CardTitle>
              <p className="text-gray-500 text-sm md:text-base">{user.email}</p>
            </div>
            <Badge className={`mt-2 sm:mt-0 px-4 py-2 ${user.isBlocked ? "bg-red-500" : "bg-green-500"}`}>
              {user.isBlocked ? "Blocked" : "Active"}
            </Badge>
          </CardHeader>
        </Card>

        {/* User Details Section */}
        <UserDetails user={user} />

        {/* Actions */}
        <div className="flex flex-col sm:flex-row justify-end sm:space-x-4 mt-4">
          {user.isBlocked ? (
            <Button variant="destructive" className="w-full sm:w-auto mb-2 sm:mb-0">Unblock User</Button>
          ) : (
            <Button variant="outline" className="w-full sm:w-auto mb-2 sm:mb-0">Block User</Button>
          )}
          <Button variant="outline" className="w-full sm:w-auto">Edit</Button>
        </div>
      </div>
    </div>
  );
}
