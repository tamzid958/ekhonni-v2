import React from "react";
import { TabsDemo } from "@/components/TabsDemo";

export default function ProfilePage() {
  return (
    <div className="bg-[#FAF7F0]">
      <div className="p-20 max-w-4xl mx-auto min-h-screen">
        <main className="flex-1 flex items-center justify-center p-0 m-0">
          <TabsDemo />
        </main>
      </div>
    </div>

  );
}
