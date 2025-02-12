import React from 'react';
import { AppSidebar } from './components/AppSidebar';

const AdminLayout = ({ children }) => {
  return (
    <div>
      <AppSidebar />
      <main>
        {/*<SidebarTrigger />*/}
        {children}
      </main>

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