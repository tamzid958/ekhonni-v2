'use client';

import React from 'react';
import { useRoles } from '../hooks/useRoles';
import { useSession } from 'next-auth/react';
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Skeleton } from "@/components/ui/skeleton";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

const Roles = () => {
  const { data: session, status } = useSession();
  const { allRolesList, reverseRoleMapping, isLoading, error } = useRoles(session?.user?.id, session?.user?.token);

  return (
    <div className="flex items-center justify-center w-[1220px] min-h-screen bg-white p-6">
      <Card className="w-full max-w-2xl shadow-lg bg-white">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl font-bold text-gray-800">User Roles</CardTitle>
        </CardHeader>
        <CardContent>
          {isLoading ? (
            <div className="space-y-4">
              <Skeleton className="h-6 w-full" />
              <Skeleton className="h-6 w-3/4" />
              <Skeleton className="h-6 w-1/2" />
            </div>
          ) : error ? (
            <Alert variant="destructive">
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>Failed to load roles. Please try again later.</AlertDescription>
            </Alert>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="w-1/3 text-center">Role ID</TableHead>
                  <TableHead className="w-1/3 text-center">Role Name</TableHead>
                  <TableHead className="w-1/3 text-center">Reverse Mapping</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {allRolesList.map((role) => (
                  <TableRow key={role.id}>
                    <TableCell className="text-center">{role.id}</TableCell>
                    <TableCell className="text-center font-medium">{role.name}</TableCell>
                    <TableCell className="text-center">{reverseRoleMapping[role.name]}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default Roles;
