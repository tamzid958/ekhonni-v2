import React from 'react';
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';

const AdminLayout = ({ children }) => {
  return (
    <div className="flex min-h-screen">
      <SidebarProvider>
        <main>

          <SidebarTrigger />
          {children}
        </main>
      </SidebarProvider>

    </div>
  );
};

export default AdminLayout;