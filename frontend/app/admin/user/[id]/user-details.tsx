'use client';
import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";

type User = {
  id: string;
  name: string;
  email: string;
  roleName: string;
  verified: boolean;
  isBlocked: boolean;
  profileImage: string;
  createdAt: string;
  updatedAt: string;
  deletedAt?: string | null;
  address: string;
};



export default function UserDetails({ user }: { user: User }) {
  return (
    <Card className="shadow-lg border rounded-lg">
      <CardHeader>
        <CardTitle>User Details</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="grid grid-cols-2 gap-4">
          <DetailRow label="Role" value={user.roleName} />
          <DetailRow label="Verified" value={user.verified ? "Yes" : "No"} />
          <DetailRow label="Created At" value={new Date(user.createdAt).toLocaleString()} />
          <DetailRow label="Updated At" value={new Date(user.updatedAt).toLocaleString()} />
          <DetailRow label="Deleted At" value={user.deletedAt ? new Date(user.deletedAt).toLocaleString() : "N/A"} />
          <DetailRow label="Address" value={user.address} />
        </div>
      </CardContent>
    </Card>
  );
}

const DetailRow = ({ label, value }: { label: string; value: string }) => (
  <div>
    <p className="text-gray-500 font-semibold">{label}</p>
    <p className="text-gray-700">{value}</p>
    <Separator className="my-2" />
  </div>
);
