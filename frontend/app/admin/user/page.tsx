
'use client';
import React, { useEffect, useRef, useState } from 'react';
import { Button } from '@/components/ui/button';
import { FaArrowDown, FaFileCsv, FaUserPlus } from 'react-icons/fa6';
import { Card, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FaArrowUp } from 'react-icons/fa';
import { Separator } from "@/components/ui/separator"
import { User,blockedUser, columns, blockedUserColumns } from './columns';
import { DataTable } from "../components/data-table"
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import Loading from '@/components/Loading';
import fetcher from '@/data/services/fetcher';
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { useRoles } from '../hooks/useRoles';
import { exportUserDataToCSV } from '../utility/exportUserDataToCSV';
import { inviteUser } from '../utility/inviteUserViaEmail';
import {getUserStats } from '../utility/filterUserByDate';
import { Select, SelectContent, SelectItem } from '@radix-ui/react-select';
import { SelectTrigger, SelectValue } from '@/components/ui/select';
import { useUsers } from '../hooks/useUser';

const timePeriodLabels = {
  all: "All Time",
  week: "This Week",
  month: "This Month",
  year: "This Year",
};

export default  function User  () {

  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  const { allRolesList, isLoading: isLoadingRole , error: roleError } = useRoles(userId, userToken);

  const [selectedTimePeriod, setSelectedTimePeriod] = useState<"all" | "week" | "month" | "year">("all");
  const [stats, setStats] = useState({ userCount: 0, growthPercentage: "0.00" });

  const { allUsers, isLoading, allUserError,  processUsers } = useUsers(userId, userToken);

  const { data: activeUsers, error: activeError , isLoading: isLoadingActive } = useSWR(
    userId ? `/api/v2/admin/user/active` : null,
    async (url) => {
      const fetchedData = await fetcher(url, userToken);
      fetchedData.content = fetchedData.content.sort((a, b) => a.id - b.id);
      return fetchedData;
    }
  );

  const { data: deletedUsers, error: deletedError, isLoading:isLoadingDelete } = useSWR(
    userId ? `/api/v2/admin/user/delete` : null,
    async (url) => {
      const fetchedData = await fetcher(url, userToken);
      fetchedData.content = fetchedData.content.sort((a, b) => a.id - b.id);
      return fetchedData;
    }
  );
  const { data: blockedUsers, error: blockedError, isLoading: isLoadingBlock } = useSWR(
    userId ? `/api/v2/admin/user/block` : null,
    async (url) => {
      const fetchedData = await fetcher(url, userToken);
      fetchedData.content = fetchedData.content.sort((a, b) => a.id - b.id);
      return fetchedData;
    }
  );
  const adminRoleId = allRolesList?.find((role) => role.name === "ADMIN")?.id;

  const { data: admin, error: adminError, isLoading: isLoadingAdmin } = useSWR(
    userId  && adminRoleId ? `/api/v2/role/${adminRoleId}/users/` : null,
    async (url) => {
      const fetchedData = await fetcher(url, userToken);
      fetchedData.content = fetchedData.content.sort((a, b) => a.id - b.id);
      return fetchedData;
    }
  );

  const totalAdmins = admin?.content?.length ?? 0;

  const processedUsers  = allUsers ?? [];

  function createTestUser(createdAt) {
    return {
      verified: false,
      roleName: "user",
      updatedAt: new Date().toISOString(),
      deletedAt: null,
      profileImage: "https://example.com/profile.jpg",
      createdAt: new Date(createdAt).toISOString(),
      isBlocked : true,
      email: `user${Math.floor(Math.random() * 1000)}@example.com`,
      name: `Test User ${Math.floor(Math.random() * 1000)}`,
      id: crypto.randomUUID(), // Generates a random UUID
      address: "123 Test Street, City, Country"
    };
  }
  const test = [
    createTestUser("2024-01-01T04:47:00.329Z"), // Last week
    createTestUser("2024-01-01T10:00:00.329Z"), // This week
    createTestUser("2024-01-01T08:30:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"), // Last week
    createTestUser("2024-01-01T10:00:00.329Z"), // This week
    createTestUser("2024-01-01T08:30:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"), // Last week
    createTestUser("2024-01-01T10:00:00.329Z"), // This week
    createTestUser("2024-01-01T08:30:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"), // Last week
    createTestUser("2024-01-01T10:00:00.329Z"), // This week
    createTestUser("2024-01-01T08:30:00.329Z"),
    createTestUser("2024-01-01T04:47:00.329Z"),// Last week
    createTestUser("2025-01-01T10:00:00.329Z"), // This week
    createTestUser("2025-01-01T08:30:00.329Z"),
    createTestUser("2025-01-01T04:47:00.329Z"), // Last week
    createTestUser("2025-01-01T10:00:00.329Z"), // This week
    createTestUser("2025-01-01T08:30:00.329Z"),
    createTestUser("2025-01-01T04:47:00.329Z"), // Last week
    createTestUser("2025-01-01T10:00:00.329Z"), // This week
    createTestUser("2025-01-01T08:30:00.329Z") ,
    createTestUser("2025-01-01T08:30:00.329Z"),
    createTestUser("2025-01-01T04:47:00.329Z"), // Last week
    createTestUser("2025-01-01T10:00:00.329Z"),
    createTestUser("2025-01-01T08:30:00.329Z"),
    createTestUser("2025-01-01T04:47:00.329Z"), // Last week
    createTestUser("2025-01-01T10:00:00.329Z"),
    // Older than last week
  ];

  const processedTestUser = processUsers(test);

  processedUsers.push(...processedTestUser);
  const totalUsers = processedUsers.length;
  useEffect(() => {
    setStats(getUserStats(processedUsers, selectedTimePeriod));
  }, [selectedTimePeriod]);


  if (status === "loading" || isLoading || isLoadingAdmin || isLoadingRole) {
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

  else if(  allUserError  || adminError || roleError)
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
            Users
          </h1>
          <h2 className="text-black font-sans text-xl">
            Find all the users here
          </h2>
        </div>

        <div className="flex items-center gap-2.5 justify-end w-full md:w-1/2  p-4">
          <Button
            variant="outline"
            className="bg-black text-white flex items-center justify-center gap-2"
            onClick={() => exportUserDataToCSV(allUsers)}
            aria-label="Export to CSV"
          >
            <FaFileCsv className="text-xl" />
            Export to CSV
          </Button>

          <Button
            variant="outline"
            className="bg-black text-white flex items-center justify-center gap-2"
            onClick={() =>  inviteUser('hafiz.sust333@gmail.com')}
            aria-label="Invite a User"
          >
            <FaUserPlus className="text-xl" />
            Invite a User
          </Button>

        </div>
      </div>

      {/* Middle section */}
      <div className="flex max-w-[1220px] bg-green-100 rounded-xl m-4 p-4 gap-2.5  h-36">
        <div className="flex-1 p-1">
          <Card className="flex flex-col bg-white hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
            <div className="flex items-start justify-between">
              <div className="flex flex-col">

                <CardTitle className="flex text-gray-500 mb-2 text-xl">
                  Total Users ({timePeriodLabels[selectedTimePeriod]})
                </CardTitle>
                <h1 className="text-4xl font-bold">{stats.userCount}</h1>
              </div>
              <div className="flex flex-col gap-2.5 items-end">
                <div className="relative flex items-end gap-2.5 justify-end w-[50px] md:w-[50px]">
                  <Select onValueChange={(value) => setSelectedTimePeriod(value as any)} value={selectedTimePeriod}>
                    <SelectTrigger className="w-[50px] h-[30px] bg-white border rounded-md text-sm shadow-sm">
                      <SelectValue placeholder="Select Time Period" />
                    </SelectTrigger>
                    <SelectContent className="bg-white border rounded-md shadow-md  w-full" position="item-aligned">
                      {Object.entries(timePeriodLabels).map(([key, label]) => (
                        <SelectItem
                          key={key}
                          value={key}
                          className="cursor-pointer hover:bg-gray-100 rounded-md p-2"
                        >
                          {label}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <Badge
                  className={`text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 ${
                    stats.growthPercentage >= 0 ? 'bg-green-500' : 'bg-red-500'
                  }`}
                >
                  {stats.growthPercentage >= 0 ? <FaArrowUp /> : <FaArrowDown />}
                  {stats.growthPercentage}%
                </Badge>
              </div>
            </div>
          </Card>
        </div>
        <div className="flex-1 p-1">
          <Card className="flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
            <div className="flex items-start justify-between">
              <div className="flex flex-col">
                <CardTitle className="flex text-gray-500 mb-2 text-xl">Admins</CardTitle>
                <h1 className="text-4xl font-bold">{totalAdmins}</h1>
              </div>
              <div className="flex mt-6">
                <Badge
                  className={`text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500`}
                >
                  {((totalAdmins / totalUsers) * 100).toFixed(2)}%
                </Badge>
              </div>
            </div>
          </Card>
        </div>
        <div className="flex-1 p-1">
          <Card className="flex flex-col hover:bg-brand-bright justify-start p-4 hover:drop-shadow-xl border-black">
            <div className="flex items-start justify-between">
              <div className="flex flex-col">
                <CardTitle className="flex text-gray-500 mb-2 text-xl">Moderators and Others</CardTitle>
                <h1 className="text-4xl font-bold">{2 * totalAdmins}</h1>
              </div>
              <div className="flex mt-6">
                <Badge
                  className={`text-white px-3 py-1 rounded-full text-sm font-bold flex items-center gap-1 bg-green-500`}
                >
                  {(2 * (totalAdmins / totalUsers) * 100).toFixed(2)}%
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
              <TabsContent  value="blocked" className = "flex min-w-[1220px] min-h[400px] ">
                  <DataTable  key={blockedUsers?.content?.length ?? 0}  columns={blockedUserColumns} data={blockedUsers?.content ?? []} />
              </TabsContent>
              <TabsContent value="deleted" className = "flex min-w-[1220px] min-h[400px] ">
                <DataTable  key={deletedUsers?.content?.length ?? 0} columns={columns} data={deletedUsers?.content ?? []} />
              </TabsContent>
            </Tabs>
          </div>
        </section>
      </div>
    </div>
    </div>
  );
}

