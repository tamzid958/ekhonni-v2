'use client';

import React from 'react';
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


  const handleSaveChanges=() =>{
    alert("Save new information");
  }

  const handleSavePassword=() =>{
    alert("Save new Password");
  }

  return (
    <Tabs defaultValue="account" className="w-[400px] bg-white rounded-lg shadow-md">
      {/* Tab List */}
      <TabsList className="grid w-full grid-cols-2 mb-4">
        <TabsTrigger value="account" className="px-4 py-2 font-medium">Account</TabsTrigger>
        <TabsTrigger value="password" className="px-4 py-2 font-medium">Password</TabsTrigger>
      </TabsList>

      {/* Account Tab Content */}
      <TabsContent value="account">
        <Card>
          <CardHeader>
            <CardTitle>Account</CardTitle>
            <CardDescription>
              Update your account details. Click save when you're done.
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="name">Name</Label>
              <Input id="name" defaultValue="John Doe" />
            </div>
            {/*<div>*/}
            {/*  <Label htmlFor="username">Username</Label>*/}
            {/*  <Input id="username" defaultValue="@johndoe" />*/}
            {/*</div>*/}
            <div>
              <Label htmlFor="email">Email</Label>
              <Input id="email" defaultValue="john.doe@example.com" />
            </div>
            <div>
              <Label htmlFor="location">Location</Label>
              <Input id="location" defaultValue="New York, USA" />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSaveChanges}>Save changes</Button>
          </CardFooter>
        </Card>
      </TabsContent>

      {/* Password Tab Content */}
      <TabsContent value="password">
        <Card>
          <CardHeader>
            <CardTitle>Password</CardTitle>
            <CardDescription>
              Change your password here. After saving, you'll be logged out.
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <Label htmlFor="current-password">Current password</Label>
              <Input id="current-password" type="password" />
            </div>
            <div>
              <Label htmlFor="new-password">New password</Label>
              <Input id="new-password" type="password" />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" onClick={handleSavePassword}>Save password</Button>
          </CardFooter>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
