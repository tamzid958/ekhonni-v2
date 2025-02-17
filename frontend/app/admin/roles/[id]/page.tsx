"use client";
import React from 'react';
import { useParams } from "next/navigation";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import UserDetails from "../../components/userDetails";
import { useSession } from 'next-auth/react';
import { useUsers } from '../../hooks/useUser';
import Loading from '@/components/Loading';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import { FaPlus } from 'react-icons/fa';
import { RoleDialog } from '../../components/RoleDialog';
import { Separator } from '@/components/ui/separator';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { DataTable } from '../../components/data-table';
import { columns, privilegeColumns } from '../columns';
import { useRoles } from '../../hooks/useRoles';

export default function RoleDetails() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const { allRolesList, processedRoles,   error, roleMapping } = useRoles(session?.user?.id, session?.user?.token);


  const { id } = useParams();
  const role = roleMapping[id];

  const { data, error: privilegeError, isLoading } = useSWR(
    userId ? [`/api/v2/role/${id}/privilege/`, userToken] : null,
    async ([url, token]) => {
      const fetchedData = await fetcher(url, token);
      return fetchedData?.content ?? [];
    }
  );

  console.log("Retrieved Privilege", data);

  if (!id) return <p className="text-red-500">User ID not found.</p>;
  if (!data) return <div className="text-center text-gray-600">PrivilegeNotFound</div>;

  if (status === "loading" || isLoading) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <Loading />
      </div>
    );

  }
  if (!session) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <div className="text-center bg-white p-6 md:p-8 rounded-lg shadow-lg">
          <h1 className="text-xl md:text-2xl font-bold mb-4">Access Denied</h1>
          <p className="text-sm md:text-base">You need to be signed in to view this page.</p>
        </div>
      </div>
    );
  }

  if (privilegeError) {
    return (
      <div className="flex justify-center items-center h-screen w-screen p-4">
        <div className="text-center bg-white p-6 md:p-8 rounded-lg shadow-lg">
          <h1 className="text-xl md:text-2xl font-bold mb-4">Error</h1>
          <p className="text-sm md:text-base">Failed to Load User Data</p>
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
             Privilages for
            </h1>
            <h2 className="text-black font-sans text-xl">
              {role}
            </h2>
          </div>

          <div className="flex items-center gap-2.5 justify-end w-full md:w-1/2  p-4">

            <Button
              variant="outline"
              className="bg-black text-white flex items-center justify-center gap-2"
              onClick={() =>
              {
                alert("new Privilege add")
              }}
              aria-label="Add New Role"
            >
              <FaPlus className="text-xl" />
              Add New Privilege to {role}
            </Button>
            {/*<RoleDialog*/}
            {/*  token = {session.user.token}*/}
            {/*  isOpen={isDialogOpen}*/}
            {/*  onClose={() => setDialogOpen(false)}*/}
            {/*  onSave={handleSave}*/}
            {/*  existingRoles={allRolesList}*/}
            {/*/>*/}

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
                  {/*<h1 className="text-4xl font-bold">{processedRoles.length}</h1>*/}
                </div>
              </div>
            </Card>
          </div>
          <div className="flex-1 p-1">
            <Card className="flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">
                  <CardTitle className="flex text-gray-500 mb-2 text-xl">Active Roles</CardTitle>
                  {/*<h1 className="text-4xl font-bold">{processedRoles.length}</h1>*/}
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
                  <TabsTrigger value="all" >All Previliges(0 )</TabsTrigger>
                  {/*<TabsTrigger value="active">Active Users({activeUsers?.content?.length?? 0})</TabsTrigger>*/}
                  {/*<TabsTrigger value="blocked">Blocked Users({blockedUsers?.content?.length?? 0})</TabsTrigger>*/}
                  {/*<TabsTrigger value="deleted">Deleted Users({deletedUsers?.content?.length?? 0})</TabsTrigger>*/}
                </TabsList>
                <TabsContent value="all" className = "flex w-[1220px] min-h[400px] ">
                  <DataTable key = {data.length} columns={ privilegeColumns} data={data} userType={"role"}/>
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
