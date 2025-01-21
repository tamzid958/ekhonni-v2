import React from 'react';
import { SidebarProvider } from '@/components/ui/sidebar';
import { AppSidebar } from './components/AppSidebar';

const AdminLayout = ({ children }) => {
  return (
    <div>
      <SidebarProvider>
        <AppSidebar />

        <main>
          {children}
        </main>
      </SidebarProvider>

    </div>
  );
};

export default AdminLayout;