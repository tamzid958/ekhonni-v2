'use client';

import React, { useState } from 'react';
import { useSession } from 'next-auth/react';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from '@/components/ui/tabs';
import { signOut } from "next-auth/react";
import { useRouter } from "next/navigation";

export function TabsDemo() {
  const { data: session } = useSession();
  const userId = session?.user?.id;
  const token = session?.user?.token;

  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [location, setLocation] = useState('');
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const router = useRouter();

  // ✅ Check if email or password form is filled for validation
  const isPasswordFormValid = currentPassword.trim() !== '' && newPassword.trim() !== '';
  const isEmailFormValid = email.trim() !== '';

  const handleSaveChanges = async () => {
    if (!userId || !token) {
      alert("User not authenticated.");
      return;
    }

    const profileUpdateUrl = `http://localhost:8080/api/v2/user/${userId}`;
    const emailUpdateUrl = `http://localhost:8080/api/v2/user/${userId}/change-email`;

    // ✅ Only send the fields that have values
    const updatedProfile: any = {};
    if (name.trim()) updatedProfile.name = name;
    if (phone.trim()) updatedProfile.phone = phone;
    if (location.trim()) updatedProfile.address = location;

    try {
      if (Object.keys(updatedProfile).length > 0) {
        // ✅ Update only name, phone, or location if provided
        const profileResponse = await fetch(profileUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedProfile),
        });

        if (!profileResponse.ok) throw new Error("Failed to update profile");

        alert("Profile updated successfully!");
      }

      if (email.trim()) {
        // ✅ Only update email if provided
        const emailResponse = await fetch(emailUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ email }),
        });

        if (!emailResponse.ok) throw new Error("Failed to update email");

        alert("Email updated successfully! You'll be logged out.");
        await signOut({ redirect: false });
        setTimeout(() => router.push("/auth/login"), 500);
      }

    } catch (error) {
      console.error("Error updating profile or email:", error);
      alert("Failed to update profile or email. Please try again.");
    }
  };

  const handleSavePassword = async () => {
    if (!userId || !token) {
      alert("User not authenticated.");
      return;
    }

    const passwordUpdateUrl = `http://localhost:8080/api/v2/user/${userId}/change-password`;
    const passwordData = { currentPassword, newPassword };

    try {
      const response = await fetch(passwordUpdateUrl, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(passwordData),
      });

      if (!response.ok) throw new Error("Failed to update password");

      alert("Password updated successfully! You'll be logged out.");
      await signOut({ redirect: false });
      setTimeout(() => router.push("/auth/login"), 500);

    } catch (error) {
      console.error("Error updating password:", error);
      alert("Failed to update password. Please try again.");
    }
  };

  return (
    <Tabs defaultValue="account" className="w-[400px] bg-white rounded-lg shadow-md">
      <TabsList className="grid w-full grid-cols-2 mb-4">
        <TabsTrigger value="account" className="px-4 py-2 font-medium">
          Account
        </TabsTrigger>
        <TabsTrigger value="password" className="px-4 py-2 font-medium">
          Password
        </TabsTrigger>
      </TabsList>

      {/* Account Tab */}
      <TabsContent value="account">
        <Card>
          <CardHeader>
            <CardTitle>Account</CardTitle>
            <CardDescription>Update any account detail. Fields are optional.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="name">Name</Label>
              <Input id="name" placeholder="Your name" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="email">Email (Logout required)</Label>
              <Input id="email" placeholder="Your email" value={email} onChange={(e) => setEmail(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="phone">Phone</Label>
              <Input id="phone" placeholder="Your phone" value={phone} onChange={(e) => setPhone(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="location">Location</Label>
              <Input id="location" placeholder="Your location" value={location} onChange={(e) => setLocation(e.target.value)} />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSaveChanges} disabled={!name && !phone && !email && !location}>
              Save changes
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>

      {/* Password Tab */}
      <TabsContent value="password">
        <Card>
          <CardHeader>
            <CardTitle>Password</CardTitle>
            <CardDescription>Change your password (Logout required).</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="current-password">Current Password *</Label>
              <Input id="current-password" type="password" placeholder="Your current password" value={currentPassword} onChange={(e) => setCurrentPassword(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="new-password">New Password *</Label>
              <Input id="new-password" type="password" placeholder="Your new password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSavePassword} disabled={!isPasswordFormValid}>
              Save password
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
