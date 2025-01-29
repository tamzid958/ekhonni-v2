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
import { router } from 'next/client';
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
  const [showPassword, setShowPassword] = useState(false);
  const [preview, setPreview] = useState<string | null>(null);
  const router = useRouter();


  const isAccountFormValid = name.trim() !== '' && phone.trim() !== '' && email.trim() !== '' && location.trim() !== '';
  const isPasswordFormValid = currentPassword.trim() !== '' && newPassword.trim() !== '';


  const handleSaveChanges = () => {
    if (!userId || !token) {
      alert("User not authenticated.");
      return;
    }

    const updateProfileAndEmail = async () => {
      const profileUpdateUrl = `http://localhost:8080/api/v2/user/${userId}`;
      const emailUpdateUrl = `http://localhost:8080/api/v2/user/${userId}/change-email`;

      const updatedProfile = { name, phone, address: location };
      const emailData = { email };

      try {
        const profileResponse = await fetch(profileUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedProfile),
        });

        if (!profileResponse.ok) throw new Error("Failed to update profile");

        const emailResponse = await fetch(emailUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(emailData),
        });

        if (!emailResponse.ok) throw new Error("Failed to update email");

        alert("Email updated successfully! You'll be logged out.");

        // Log out and redirect to login page
        await signOut({ redirect: false }); // Logout without redirecting
        router.push("/auth/login"); // Redirect to login page

      } catch (error) {
        //console.error("Error updating profile and email:", error);
        alert("Failed to update profile and email. Please try again.");
      }
    };

    updateProfileAndEmail();
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
      console.log("response is", response);
      if (!response.ok) throw new Error("Failed to update password");

      alert("Password updated successfully! You'll be logged out.");

      // Log out and redirect to login page
      await signOut({ redirect: false });
      router.push("/auth/login");

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
            <CardDescription>Update your account details. All fields are required.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="name">Name *</Label>
              <Input id="name" placeholder="Your name" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="email">Email *</Label>
              <Input id="email" placeholder="Your email" value={email} onChange={(e) => setEmail(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="phone">Phone *</Label>
              <Input id="phone" placeholder="Your phone" value={phone} onChange={(e) => setPhone(e.target.value)} />
            </div>
            <div>
              <Label htmlFor="location">Location *</Label>
              <Input id="location" placeholder="Your location" value={location} onChange={(e) => setLocation(e.target.value)} />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSaveChanges} disabled={!isAccountFormValid}>
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
            <CardDescription>Change your password here. All fields are required.</CardDescription>
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
