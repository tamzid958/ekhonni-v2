import React from 'react';
import { AppSidebar } from './components/AppSidebar';
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';

const AdminLayout = ({ children }) => {
  return (
    <div>
      <SidebarProvider>
        <AppSidebar />
        <SidebarTrigger />


        <main>
          {/*<SidebarTrigger />*/}
          {children}
        </main>
      </SidebarProvider>

    </div>
  );
};

export default AdminLayout;

// const Category = () => {
//   const subCategories = []
//   return (
//     {
//       subCategories.map((sub) => <Category></Category>)
//     }
//   )
// }