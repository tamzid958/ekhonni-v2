
import React from 'react';
import { Button } from '@/components/ui/button';

import { FaArrowDown, FaFileCsv, FaUserPlus } from 'react-icons/fa6';
import { Card, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FaArrowUp } from 'react-icons/fa';
import { Separator } from "@/components/ui/separator"
import { User , columns } from "./columns"
import { DataTable } from "../components/data-table"

async function getUsers(): Promise<User[]> {
  return [
    {
      id: "1",
      name: "Alice Johnson",
      email: "alice@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/1.jpg",
      status: "pending",
      lastSeen: "2025-01-30T10:30:00Z",
    },
    {
      id: "2",
      name: "Bob Smith",
      email: "bob@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/2.jpg",
      status: "processing",
      lastSeen: "2025-01-30T12:15:00Z",
    },
    {
      id: "3",
      name: "Charlie Brown",
      email: "charlie@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/3.jpg",
      status: "success",
      lastSeen: "2025-01-30T09:45:00Z",
    },
    {
      id: "4",
      name: "Diana Green",
      email: "diana@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/4.jpg",
      status: "failed",
      lastSeen: "2025-01-29T15:00:00Z",
    },
    {
      id: "5",
      name: "Ethan White",
      email: "ethan@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/5.jpg",
      status: "pending",
      lastSeen: "2025-01-30T08:20:00Z",
    },
    {
      id: "6",
      name: "Fiona Black",
      email: "fiona@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/women/6.jpg",
      status: "processing",
      lastSeen: "2025-01-29T18:35:00Z",
    },
    {
      id: "7",
      name: "George Clark",
      email: "george@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/7.jpg",
      status: "success",
      lastSeen: "2025-01-30T11:05:00Z",
    },
    {
      id: "8",
      name: "Hannah Lewis",
      email: "hannah@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/8.jpg",
      status: "failed",
      lastSeen: "2025-01-29T14:25:00Z",
    },
    {
      id: "9",
      name: "Isaac Turner",
      email: "isaac@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/9.jpg",
      status: "pending",
      lastSeen: "2025-01-30T07:55:00Z",
    },
    {
      id: "10",
      name: "Julia Adams",
      email: "julia@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/women/10.jpg",
      status: "processing",
      lastSeen: "2025-01-29T21:10:00Z",
    },
    {
      id: "11",
      name: "Alice Johnson",
      email: "alice1@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/1.jpg",
      status: "pending",
      lastSeen: "2025-01-30T10:30:00Z",
    },
    {
      id: "12",
      name: "Bob Smith",
      email: "bob1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/2.jpg",
      status: "processing",
      lastSeen: "2025-01-30T12:15:00Z",
    },
    {
      id: "13",
      name: "Charlie Brown",
      email: "charlie1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/3.jpg",
      status: "success",
      lastSeen: "2025-01-30T09:45:00Z",
    },
    {
      id: "14",
      name: "Diana Green",
      email: "diana1@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/4.jpg",
      status: "failed",
      lastSeen: "2025-01-29T15:00:00Z",
    },
    {
      id: "15",
      name: "Ethan White",
      email: "ethan1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/5.jpg",
      status: "pending",
      lastSeen: "2025-01-30T08:20:00Z",
    },
    {
      id: "16",
      name: "Fiona Black",
      email: "fiona1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/women/6.jpg",
      status: "processing",
      lastSeen: "2025-01-29T18:35:00Z",
    },
    {
      id: "17",
      name: "George Clark",
      email: "george1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/7.jpg",
      status: "success",
      lastSeen: "2025-01-30T11:05:00Z",
    },
    {
      id: "18",
      name: "Hannah Lewis",
      email: "hannah1@example.com",
      role: "admin",
      image: "https://randomuser.me/api/portraits/women/8.jpg",
      status: "failed",
      lastSeen: "2025-01-29T14:25:00Z",
    },
    {
      id: "19",
      name: "Isaac Turner",
      email: "isaac1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/men/9.jpg",
      status: "pending",
      lastSeen: "2025-01-30T07:55:00Z",
    },
    {
      id: "20",
      name: "Julia Adams",
      email: "julia1@example.com",
      role: "user",
      image: "https://randomuser.me/api/portraits/women/10.jpg",
      status: "processing",
      lastSeen: "2025-01-29T21:10:00Z",
    },

    // ...
  ]
}
export default async function User  () {

  const data = await getUsers()


  return (
    <div className="flex w-[1220px] flex-col  min-h-screen overflow-auto bg-white">

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
      <div className="flex max-w-[1220px] overflow-hidden rounded-xl m-4 p-4 bg-gray-100 gap-2.5  h-36">
        <div className="flex-1    p-1 ">
            <Card className=" flex flex-col justify-start p-4">
              <div className="flex items-start justify-between">
                <div className="flex flex-col">
                  <CardTitle className="flex text-gray-500 mb-2 text-xl">Total User</CardTitle>
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
          <Card className=" flex flex-col justify-start p-4">
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
          <Card className=" flex flex-col justify-start p-4">
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
            <div className="flex mb-4 ">
            <DataTable columns={columns} data={data} />
            </div>
          </div>
        </section>
      </div>

    </div>
  );
}

