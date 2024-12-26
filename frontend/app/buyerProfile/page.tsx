// pages/ProfilePage.tsx

import React from "react";
import { TabsDemo } from "@/components/TabsDemo";

export default function ProfilePage() {
  return (
    <div className="flex flex-col min-h-screen bg-gray-50">
      {/* Header Section */}
      <header className="bg-brand-bright text-black p-4 text-center">
        <h1 className="text-xl font-bold">Edit Profile</h1>
      </header>

      {/* Main Content Section */}
      <main className="flex-1 flex items-center justify-center py-8">
        <TabsDemo />
      </main>
    </div>
  );
}
