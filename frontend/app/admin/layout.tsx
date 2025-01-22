import React from 'react';
import { AppSidebar } from './components/AppSidebar';
import { SidebarProvider } from '@/components/ui/sidebar';

const AdminLayout = ({ children }) => {
  return (
    <div>
      <SidebarProvider>
        <AppSidebar />

        <main>
          {/*<SidebarTrigger />*/}
          {children}
        </main>
      </SidebarProvider>

    </div>
  );
};

export default AdminLayout;