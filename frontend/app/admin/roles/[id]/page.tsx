"use client";
import React, { useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { useSession } from 'next-auth/react';
import { useUsers } from '../../hooks/useUser';
import Loading from '@/components/Loading';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import { FaPlus } from 'react-icons/fa';
import { Separator } from '@/components/ui/separator';
import { DataTable } from '../../components/data-table';
import { privilegeColumns } from '../columns';
import { reverseRoleMapping, useRoles } from '../../hooks/useRoles';
import { PrivilegeDialog } from '../../components/PrivilegeDialog'; // Import PrivilegeDialog
import { Dialog, DialogContent, DialogTitle, DialogTrigger } from '@/components/ui/dialog';

export default function RoleDetails() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const router = useRouter();
  const { allRolesList, processedRoles, error, roleMapping } = useRoles(session?.user?.id, session?.user?.token);

  const { id } = useParams();

  const { data, error: privilegeError, isLoading } = useSWR(
    userId ? [`/api/v2/role/${id}/privilege/?page=0&size=200`, userToken] : null,
    async ([url, token]) => {
      const fetchedData = await fetcher(url, token);
      const updatedData = fetchedData?.content?.map(item => ({
        ...item,
        roles: ["SUPER_ADMIN"],
      })) ?? [];
      return updatedData;
    }
  );

  const groupPrivilegesByType = (privileges) => {
    return privileges.reduce((acc, privilege) => {
      if (!acc[privilege.type]) {
        acc[privilege.type] = [];
      }
      acc[privilege.type].push(privilege);
      return acc;
    }, {});
  };

  const [selectedRole, setSelectedRole] = useState(id);
  const [selectedType, setSelectedType] = useState("");
  const [groupedPrivileges, setGroupedPrivileges] = useState({});
  const [selectedPrivileges, setSelectedPrivileges] = useState([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  useEffect(() => {
    if (data) {
      const groupedData = groupPrivilegesByType(data);
      setGroupedPrivileges(groupedData);
      if (Object.keys(groupedData).length > 0) {
        setSelectedType(Object.keys(groupedData)[0]);
      }
    }
  }, [data]);

  useEffect(() => {
    if (selectedRole) {
      router.push(`/admin/roles/${selectedRole}`);
    }
  }, [selectedRole, router]);

  const handleRoleChange = (event) => {
    setSelectedRole(event.target.value);
  };

  const handleTypeChange = (event) => {
    setSelectedType(event.target.value);
  };

  const handlePrivilegeSelection = (privilegeId) => {
    setSelectedPrivileges(prevSelected => {
      if (prevSelected.includes(privilegeId)) {
        return prevSelected.filter(id => id !== privilegeId);
      }
      return [...prevSelected, privilegeId];
    });
  };

  const handleAssignPrivileges = () => {
    // Call the backend API to assign privileges
    console.log("Assigning privileges:", selectedPrivileges);
    // Example backend API call
    // fetch('/api/assignPrivileges', { method: 'POST', body: { roleId: selectedRole, privilegeIds: selectedPrivileges } });
    setIsDialogOpen(false); // Close dialog after assigning
  };

  const filteredPrivileges = groupedPrivileges[selectedType] || [];

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
    <div className="flex ">
      <div className="flex w-[1220px] h-[1250px] flex-col bg-white ">
        <div className="flex flex-col md:flex-row w-full">
          <div className="flex flex-col w-full md:w-1/2 p-4">
            <h1 className="text-black font-sans font-bold text-3xl">Privilages for</h1>
            <div className="flex p-4">
              <select
                value={selectedRole}
                onChange={handleRoleChange}
                className="px-4 py-2 pr-8 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 w-auto"
              >
                {Object.keys(roleMapping).map((roleId) => (
                  <option key={roleId} value={roleId}>
                    {roleMapping[roleId]}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="flex items-center gap-2.5 justify-end w-full md:w-1/2 p-4">
            <Button
              variant="outline"
              className="bg-black text-white flex items-center justify-center gap-2"
              onClick={() => setIsDialogOpen(true)} // Toggle dialog visibility
              aria-label="Assign New Privilege"
            >
              <FaPlus className="text-xl" />
              Assign New Privilege
            </Button>
          </div>
        </div>

        <Separator className="flex p-0" />
        {/* Bottom section */}
        <div className="w-full h-36 mt-2">
          <section className="flex">
            <div className="flex flex-col justify-start items-start">
              <div className="flex p-4">
                <div className="relative">
                  <select
                    value={selectedType}
                    onChange={handleTypeChange}
                    className="px-4 py-2 pr-10 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    {Object.keys(groupedPrivileges).map((type) => (
                      <option key={type} value={type} className="m-2">
                        {type}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div>
                <DataTable
                  columns={privilegeColumns}
                  data={filteredPrivileges.map(priv => ({
                    ...priv,
                    isSelected: selectedPrivileges.includes(priv.id),
                    onSelect: () => handlePrivilegeSelection(priv.id)
                  }))}
                  userType="privilege"
                />
              </div>
            </div>
          </section>
        </div>
      </div>

      {/* Privilege Dialog */}
      <PrivilegeDialog
        token={userToken}
        isOpen={isDialogOpen}
        onClose={() => setIsDialogOpen(false)}
        onSave={(updatedPrivileges) => {
          // Handle the saving of updated privileges
          setSelectedPrivileges(updatedPrivileges);
        }}
        groupedPrivileges={groupedPrivileges}
        existingPrivileges={filteredPrivileges.filter((privilege) =>
          selectedPrivileges.includes(privilege.id)
        )}
      />
    </div>
  );
}
