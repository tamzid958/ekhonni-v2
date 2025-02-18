'use client';

import React, { useState } from 'react';
import { useRoles } from '../hooks/useRoles';
import { useSession } from 'next-auth/react';
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import Loading from '@/components/Loading';
import { Button } from '@/components/ui/button';

import { Badge } from '@/components/ui/badge';
import { FaArrowUp, FaPlus } from 'react-icons/fa';
import { Separator } from '@/components/ui/separator';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { DataTable } from '../components/data-table';
import {columns } from '../roles/columns';
import { RoleDialog } from '../components/RoleDialog';


const Roles = () => {
  const { data: session, status } = useSession();
  const [isDialogOpen, setDialogOpen] = useState(false);
  const { allRolesList, processedRoles,  isLoading, error } = useRoles(session?.user?.id, session?.user?.token);
  const [editingRole, setEditingRole] = useState(null);

  const handleSave =() =>
  {
    setDialogOpen(false);
    setEditingRole(null);
  }
  if (status === "loading" || isLoading ) {
    return (
      <div className="flex w-[1220px] h-[1200px] flex-col  bg-white ">
        <div className="flex justify-center flex-col items-center h-screen">
          <Loading />
        </div>
      </div>
    );
  }
  if (!session) {
    return (
      <div className="flex flex-col w-[1220px] h-[1200px] items-center bg-white ">
        <div className="flex flex-col justify-center items-center h-screen">
          <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
          <p>You need to be signed in to view this page.</p>
        </div>
      </div>
    );
  }
  else if( error)
  {
    return (
      <div className="flex w-[1220px] h-[1200px] flex-col items-center  bg-white ">
        <div className="flex justify-center flex-col items-center h-screen">
          <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
          <p>Failed to Load User Data</p>
        </div>
      </div>
    );
  }
  return (
    <div className="flex">
      <div className="flex w-[1220px] h-[1250px] flex-col  bg-white ">

        <div className="flex flex-col md:flex-row w-full">

          <div className="flex flex-col w-full md:w-1/2  p-4">
            <h1 className="text-black font-sans font-bold text-3xl">
              Roles
            </h1>
            <h2 className="text-black font-sans text-xl">
              Find all roles and previlages
            </h2>
          </div>

          <div className="flex items-center gap-2.5 justify-end w-full md:w-1/2  p-4">
            {/*<Button*/}
            {/*  variant="outline"*/}
            {/*  className="bg-black text-white flex items-center justify-center gap-2"*/}
            {/*  // onClick={() => exportUserDataToCSV(allUsers)}*/}
            {/*  aria-label="Export to CSV"*/}
            {/*>*/}
            {/*  <FaFileCsv className="text-xl" />*/}
            {/*  Export to CSV*/}
            {/*</Button>*/}

            <Button
              variant="outline"
              className="bg-black text-white flex items-center justify-center gap-2"
              onClick={() =>
              {
                setEditingRole(null);
                setDialogOpen(true)

              }}
              aria-label="Add New Role"
            >
              <FaPlus className="text-xl" />
              Add New Roles
            </Button>
            <RoleDialog
              token = {session.user.token}
              isOpen={isDialogOpen}
              onClose={() => setDialogOpen(false)}
              onSave={handleSave}
              existingRoles={allRolesList}
            />

          </div>
        </div>

        {/* Middle section */}
        <div className="flex max-w-[1220px] bg-green-100 rounded-xl m-4 p-4 gap-2.5  h-36">
          <div className="flex-1 p-1">
            <Card className="flex flex-col bg-white hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">

                  <CardTitle className="flex text-gray-500 mb-2 text-xl">
                   Total Roles
                  </CardTitle>
                  <h1 className="text-4xl font-bold">{processedRoles.length}</h1>
                </div>
              </div>
            </Card>
          </div>
          <div className="flex-1 p-1">
            <Card className="flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">
                  <CardTitle className="flex text-gray-500 mb-2 text-xl">Active Roles</CardTitle>
                  <h1 className="text-4xl font-bold">{processedRoles.length}</h1>
                </div>
                <div className="flex mt-6">
                  <Badge
                    className={`text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500`}
                  >
                    {100}%
                  </Badge>
                </div>
              </div>
            </Card>
          </div>
          <div className="flex-1 p-1">
            <Card className="flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">
                  <CardTitle className="flex text-gray-500 mb-2 text-xl">Archived Roles</CardTitle>
                  <h1 className="text-4xl font-bold">{0}</h1>
                </div>
                <div className="flex mt-6">
                  <Badge
                    className={`text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500`}
                  >
                    {0}%
                  </Badge>
                </div>
              </div>
            </Card>
          </div>
        </div>
        <Separator className="flex  p-0" />
        {/* Bottom section */}
        <div className="w-full  h-36 mt-2">
          <section className=" flex">
            <div className="flex flex-col justify-start items-start">
              <Tabs defaultValue="all" className="w-[400px] pl-4">
                <TabsList>
                  <TabsTrigger value="all" >All Roles({processedRoles.length} )</TabsTrigger>
                  {/*<TabsTrigger value="active">Active Users({activeUsers?.content?.length?? 0})</TabsTrigger>*/}
                  {/*<TabsTrigger value="blocked">Blocked Users({blockedUsers?.content?.length?? 0})</TabsTrigger>*/}
                  {/*<TabsTrigger value="deleted">Deleted Users({deletedUsers?.content?.length?? 0})</TabsTrigger>*/}
                </TabsList>
                <TabsContent value="all" className = "flex w-[1220px] min-h[400px] ">
                  <DataTable key = {processedRoles.length} columns={columns} data={processedRoles} userType={"role"}/>
                </TabsContent>
                {/*<TabsContent value="active" className = "flex w-[1220px] min-h[400px] ">*/}
                {/*  <DataTable key={activeUsers?.content?.length ?? 0} columns={columns} data={activeUsers?.content ?? []} />*/}
                {/*</TabsContent>*/}
                {/*<TabsContent  value="blocked" className = "flex min-w-[1220px] min-h[400px] ">*/}
                {/*  <DataTable  key={blockedUsers?.content?.length ?? 0}  columns={blockedUserColumns} data={blockedUsers?.content ?? []} />*/}
                {/*</TabsContent>*/}
                {/*<TabsContent value="deleted" className = "flex min-w-[1220px] min-h[400px] ">*/}
                {/*  <DataTable  key={deletedUsers?.content?.length ?? 0} columns={columns} data={deletedUsers?.content ?? []} />*/}
                {/*</TabsContent>*/}
              </Tabs>
            </div>
          </section>
        </div>
      </div>
    </div>
  );
}
export default Roles;
