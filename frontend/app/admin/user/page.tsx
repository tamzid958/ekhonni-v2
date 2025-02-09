
'use client';
import React, { useEffect, useRef } from 'react';
import { Button } from '@/components/ui/button';

import { FaArrowDown, FaFileCsv, FaUserPlus } from 'react-icons/fa6';
import { Card, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FaArrowUp } from 'react-icons/fa';
import { Separator } from "@/components/ui/separator"
import { User , columns } from "./columns"
import { DataTable } from "../components/data-table"
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import Loading from '@/components/Loading';
import fetcher from '@/data/services/fetcher';
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { useRoles } from '../hooks/useRoles';

const processUsers = (users: any[]) : User[] => {
  return users.map(user => {
    let status = "ACTIVE";

    if (user.deletedAt) {
      status = "DELETED";
    } else if (user.blockedAt) {
      status = "BLOCKED";
    } else if (!user.verified) {
      status = "UNVERIFIED";
    }
    return {
      ...user,
      status,
    };
  });
};

export default  function User  () {

  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  const { allRolesList, isLoading: isLoadingRole , error: roleError} = useRoles(userId, userToken);

  const { data: allUsers, error: allError, isLoading: isLoading } = useSWR(
    userId ? `/api/v2/admin/user` : null,
    (url) => fetcher(url, userToken)
  );

  const { data: activeUsers, error: activeError , isLoading: isLoadingActive } = useSWR(
    userId ? `/api/v2/admin/user/active` : null,
    (url) => fetcher(url, userToken)
  );
  const { data: deletedUsers, error: deletedError, isLoading:isLoadingDelete } = useSWR(
    userId ? `/api/v2/admin/user/delete` : null,
    (url) => fetcher(url, userToken)
  );
  const { data: blockedUsers, error: blockedError, isLoading: isLoadingBlock } = useSWR(
    userId ? `/api/v2/admin/user/block` : null,
    (url) => fetcher(url, userToken)
  );

  const adminRoleId = allRolesList?.find((role) => role.name === "ADMIN")?.id;

  const { data: admin, error: adminError, isLoading: isLoadingAdmin } = useSWR(
    userId  && adminRoleId ? `/api/v2/role/${adminRoleId}/users/` : null,
    (url) => fetcher(url, userToken)
  );

  const totalAdmins = admin?.content?.length ?? 0;

  const processedUsers: User[] = allUsers ? processUsers(allUsers.content) : [];
  const totalUsers = processedUsers.length;

  if (status === "loading" || isLoading || isLoadingActive || isLoadingDelete || isLoadingBlock || isLoadingAdmin || isLoadingRole) {
    return (
      <div className="flex w-[1220px] h-[1200px] flex-col  bg-white ">
          <div className="flex justify-center items-center h-screen">
            <Loading />
          </div>
      </div>
    );
  }
  if (!session) {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Access Denied</h1>
        <p>You need to be signed in to view this page.</p>
      </div>
    );

  }
  else if(activeError || allError || deletedError || blockedError || adminError || roleError)
  {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Error</h1>
        <p>Failed to Load User Data </p>
      </div>
    );
  }

  return (
    <div className="flex w-[1220px] h-[1250px] flex-col  bg-white ">

      {/* Top section */}
      <div className="flex flex-col md:flex-row w-full">

        {/* Top left bar */}
        <div className="flex flex-col w-full md:w-1/2  p-4">
          <h1 className="text-black font-sans font-bold text-3xl">
            Users
          </h1>
          <h2 className="text-black font-sans text-xl">
            Find all the users here
          </h2>
        </div>

        {/* Top right bar (button) */}
        <div className="flex items-center gap-2.5 justify-end w-full md:w-1/2  p-4">
          <Button
            variant="outline"
            className="bg-black text-white flex items-center justify-center gap-2"
            //onClick={() => console.log("Export to CSV clicked")}
            aria-label="Export to CSV"
          >
            <FaFileCsv className="text-xl" />
            Export to CSV
          </Button>

          <Button
            variant="outline"
            className="bg-black text-white flex items-center justify-center gap-2"
            //onClick={() => console.log("Invite a User clicked")}
            aria-label="Invite a User"
          >
            <FaUserPlus className="text-xl" />
            Invite a User
          </Button>

        </div>
      </div>

      {/* Middle section */}
      <div className="flex max-w-[1220px] bg-green-100 rounded-xl m-4 p-4 gap-2.5  h-36">
        <div className="flex-1    p-1 ">
            <Card className=" flex flex-col hover:bg-brand-bright justify-start p-4  hover:drop-shadow-xl border-black">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">
                  <CardTitle className="flex text-gray-500 mb-2 text-xl  ">Total User</CardTitle>
                  <h1 className="text-4xl font-bold">{totalUsers}</h1>
                </div>
                <div className="flex  mt-6">
                  {/* Static Badge for Increase */}
                  <Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500">
                    <FaArrowUp />
                    12%
                  </Badge>

                  {/* Uncomment below for a static decrease example */}
                  {/* <Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-red-500">
                  <FaArrowDown />
                  8%
                </Badge> */}
                </div>
              </div>
            </Card>

        </div>

        <div className="flex-1    p-1 ">
          <Card className=" flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
            <div className="flex items-start justify-between">
              <div className="flex flex-col">
                <CardTitle className="flex text-gray-500 mb-2 text-xl">Admins</CardTitle>
                <h1 className="text-4xl font-bold">{totalAdmins}</h1>

              </div>
              <div className="flex  mt-6">
                {/* Static Badge for Increase */}
                {/*<Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500">*/}
                {/*  <FaArrowUp />*/}
                {/*  9%*/}
                {/*</Badge>*/}

                {/* Uncomment below for a static decrease example */}
                 <Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-red-500">
                  <FaArrowDown />
                  8%
                </Badge>
              </div>
            </div>
          </Card>
        </div>

        <div className="flex-1    p-1 ">
          <Card className=" flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
            <div className="flex items-start justify-between">
              <div className="flex flex-col">
                <CardTitle className="flex text-gray-500 mb-2 text-xl">Moderators and Others</CardTitle>
                <h1 className="text-4xl font-bold">1</h1>
              </div>
              <div className="flex  mt-6">
                {/* Static Badge for Increase */}
                <Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500">
                  <FaArrowUp />
                  5%
                </Badge>
                {/* Uncomment below for a static decrease example */}
                {/* <Badge className="text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-red-500">
                  <FaArrowDown />
                  8%
                </Badge> */}
              </div>
            </div>
          </Card>
        </div>

      </div>

      <Separator className="flex  p-0" />
      {/* Bottom section */}
      <div className="w-full  h-36 mt-2">
        <section className=" flex pr-4 ">
          <div className="flex flex-col justify-start items-start">
            {/*<h1 className="text-2xl mb-4 font-bold">All Users(12,000)</h1>*/}
            <Tabs defaultValue="all" className="w-[400px]">
              <TabsList>
                <TabsTrigger value="all" >All Users({processedUsers.length} )</TabsTrigger>
                <TabsTrigger value="active">Active Users({activeUsers?.content?.length?? 0})</TabsTrigger>
                <TabsTrigger value="blocked">Blocked Users({blockedUsers?.content?.length?? 0})</TabsTrigger>
                <TabsTrigger value="deleted">Deleted Users({deletedUsers?.content?.length?? 0})</TabsTrigger>
              </TabsList>
              <TabsContent value="all" className = "flex w-[1220px] min-h[400px] ">
                  <DataTable key = {processedUsers.length} columns={columns} data={processedUsers}/>
              </TabsContent>
              <TabsContent value="active" className = "flex w-[1220px] min-h[400px] ">
                  <DataTable key={activeUsers?.content?.length ?? 0} columns={columns} data={activeUsers?.content ?? []} />
              </TabsContent>
              <TabsContent key={blockedUsers?.content?.length ?? 0}  value="blocked" className = "flex min-w-[1220px] min-h[400px] ">
                  <DataTable  columns={columns} data={blockedUsers?.content ?? []} />
              </TabsContent>
              <TabsContent value="deleted" className = "flex min-w-[1220px] min-h[400px] ">
                <DataTable  key={deletedUsers?.content?.length ?? 0} columns={columns} data={deletedUsers?.content ?? []} />
              </TabsContent>
            </Tabs>
          </div>
        </section>
      </div>
    </div>
  );
}

