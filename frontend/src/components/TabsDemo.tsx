'use client';

import React, { useState } from 'react';
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs";

export function TabsDemo() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [location, setLocation] = useState("");


  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);


  const isAccountFormValid = name.trim() !== "" && email.trim() !== "" && location.trim() !== "";


  const isPasswordFormValid = currentPassword.trim() !== "" && newPassword.trim() !== "";

  const handleSaveChanges = () => {
    alert("Account information saved successfully!");

    setName('');
    setEmail('');
    setLocation('');
  };

  const handleSavePassword = () => {
    alert("Password updated successfully! You'll be logged out.");

    setCurrentPassword('');
    setNewPassword('');
    setShowPassword(false);
  };

  return (
    <Tabs defaultValue="account" className="w-[400px] bg-white rounded-lg shadow-md">

      <TabsList className="grid w-full grid-cols-2 mb-4">
        <TabsTrigger value="account" className="px-4 py-2 font-medium">Account</TabsTrigger>
        <TabsTrigger value="password" className="px-4 py-2 font-medium">Password</TabsTrigger>
      </TabsList>


      <TabsContent value="account">
        <Card>
          <CardHeader>
            <CardTitle>Account</CardTitle>
            <CardDescription>
              Update your account details. All fields are required.
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="name">
                Name <span className="text-red-500">*</span>
              </Label>
              <Input
                id="name"
                placeholder="Your name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>
            <div>
              <Label htmlFor="email">
                Email <span className="text-red-500">*</span>
              </Label>
              <Input
                id="email"
                type="email"
                placeholder="Your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div>
              <Label htmlFor="location">
                Location <span className="text-red-500">*</span>
              </Label>
              <Input
                id="location"
                placeholder="Your location"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
              />
            </div>
          </CardContent>
          <CardFooter>
            <Button
              className="w-full"
              onClick={handleSaveChanges}
              disabled={!isAccountFormValid}
            >
              Save changes
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>


      <TabsContent value="password">
        <Card>
          <CardHeader>
            <CardTitle>Password</CardTitle>
            <CardDescription>
              Change your password here. All fields are required.
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="current-password">
                Current Password <span className="text-red-500">*</span>
              </Label>
              <Input
                id="current-password"
                type={showPassword ? "text" : "password"}
                placeholder="Your current password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
              />
            </div>
            <div>
              <Label htmlFor="new-password">
                New Password <span className="text-red-500">*</span>
              </Label>
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
            <Button
              className="w-full"
              onClick={handleSavePassword}
              disabled={!isPasswordFormValid}
            >
              Save password
            </Button>
          </CardFooter>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
