
'use client';
import React from 'react';
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

export default  function User  () {

  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const role = session?.user?.role;

  const { data: userData, error, isLoading } = useSWR(
    userId ? `/api/v2/admin/users` : null,
    (url) => fetcher(url, userToken)
  );

  if (status === "loading" || isLoading) {
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
  else if(error)
  {
    return (
      <div className="flex flex-col justify-center items-center h-screen">
        <h1 className="text-2xl font-bold mb-4">Error</h1>
        <p>Something went wrong : {error}</p>
      </div>
    );
  }

  return (
    <div className="flex w-[1220px] h-[1200px] flex-col  bg-white ">

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
                  <h1 className="text-4xl font-bold">12,000</h1>
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
                <h1 className="text-4xl font-bold">200</h1>
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
                <h1 className="text-4xl font-bold">67</h1>
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
        <section className=" flex pl-4">
          <div className="flex flex-col justify-start items-start">
            <h1 className="text-2xl mb-4 font-bold">All Users(12,000)</h1>
            <div className="flex mb-4  ">
            <DataTable columns={columns} data={userData.content} />
            </div>
          </div>
        </section>
      </div>

    </div>
  );
}

