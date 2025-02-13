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
import { Toaster, toast } from "sonner";
import useSWR, { mutate } from 'swr';
import fetcher from '@/data/services/fetcher';

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
  const [profileImage, setProfileImage] = useState<File | null>(null);

  const router = useRouter();

  const { data, error, isLoading } = useSWR(
    userId ? `/api/v2/user/${userId}` : null,
    (url) => fetcher(url, token)
  );

  const isPasswordFormValid = currentPassword.trim() !== '' && newPassword.trim() !== '';

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] || null;
    setProfileImage(file);
  };

  const handleSaveChanges = async () => {
    if (!userId || !token) {
      toast.error("User not authenticated.");
      return;
    }

    const profileUpdateUrl = `/api/v2/user/${userId}`;
    const emailUpdateUrl = `/api/v2/user/${userId}/change-email`;
    const imageUpdateUrl = `/api/v2/user/${userId}/image`;

    const updatedProfile = {
      name: name.trim() || "",
      phone: phone.trim() || "",
      address: location.trim() || "",
    };

    try {
      if (Object.keys(updatedProfile).length > 0) {
        const profileResponse = await fetch(profileUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedProfile),
        });

        if (!profileResponse.ok) throw new Error("Failed to update profile");

        mutate(profileUpdateUrl); // SWR revalidation

        toast.success("Profile updated successfully!");
        setName('');
        setPhone('');
        setLocation('');
      }

      if (email.trim()) {
        const emailResponse = await fetch(emailUpdateUrl, {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ email }),
        });

        if (!emailResponse.ok) throw new Error("Failed to update email");

        setEmail('');

        toast.success("Email updated successfully! You'll be logged out.");
        await signOut({ redirect: false });
        setTimeout(() => router.push("/auth/login"), 500);
      }

      if (profileImage) {
        const formData = new FormData();
        formData.append("image", profileImage);

        const imageResponse = await fetch(imageUpdateUrl, {
          method: "PATCH",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        });

        if (!imageResponse.ok) throw new Error("Failed to update profile image");
        setProfileImage(null);
        toast.success("Profile image updated successfully!");
        mutate(imageUpdateUrl); // SWR revalidation
      }

    } catch (error) {
      console.error("Error updating profile, email, or image:", error);
      toast.error("Failed to update profile, email, or image. Please try again.");
    }
  };

  const handleSavePassword = async () => {
    if (!userId || !token) {
      alert("User not authenticated.");
      return;
    }

    const passwordUpdateUrl = `/api/v2/user/${userId}/change-password`;
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

      setCurrentPassword('');
      setNewPassword('');

      toast.success("Password updated successfully! You'll be logged out.");
      await signOut({ redirect: false });
      setTimeout(() => router.push("/auth/login"), 500);

    } catch (error) {
      console.error("Error updating password:", error);
      toast.error("Failed to update password. Please try again.");
    }
  };

  return (
    <Tabs defaultValue="account" className="w-[400px] bg-white rounded-lg shadow-md">
      <Toaster position="top-right" />
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
            <div>
              <Label htmlFor="profile-image">Profile Image</Label>
              <Input
                id="profile-image"
                type="file"
                accept="image/*"
                onChange={handleImageChange}
              />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSaveChanges} disabled={!(name || phone || email || location || profileImage)}>
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
              <Input
                id="current-password"
                type={showPassword ? "text" : "password"}
                placeholder="Your current password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
              />
            </div>
            <div>
              <Label htmlFor="new-password">New Password *</Label>
              <Input
                id="new-password"
                type={showPassword ? "text" : "password"}
                placeholder="Your new password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
            </div>
            <div className="flex items-center">
              <input
                id="show-password"
                type="checkbox"
                className="mr-2"
                checked={showPassword}
                onChange={() => setShowPassword(!showPassword)}
              />
              <Label htmlFor="show-password" className="cursor-pointer">
                Show password
              </Label>
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
