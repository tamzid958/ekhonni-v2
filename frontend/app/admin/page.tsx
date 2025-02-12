'use client';

import React from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Separator } from '@/components/ui/separator';
import { useRouter } from 'next/navigation';

const AdminIntroPage = () => {
  const router = useRouter();

  return (
    <div className="container mx-auto px-4 py-8 space-y-6">

      {/* Welcome Message */}
      <header className="space-y-4 text-center">
        <h1 className="text-4xl font-bold">Welcome to the Admin Dashboard</h1>
        <p className="text-gray-600 text-lg">
          Manage categories, products, users, and much more from one centralized location.
        </p>
      </header>

      <Separator />

      {/* Quick Links Section */}
      <section className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {/* Categories Card */}
        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle>Categories</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-gray-700">View and manage product categories and subcategories.</p>
            <Button
              className="mt-4 w-full"
              onClick={() => router.push('/admin/categories')}
            >
              Go to Categories
            </Button>
          </CardContent>
        </Card>

        {/* Products Card */}
        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle>Products</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-gray-700">Add, update, and manage products in the store.</p>
            <Button
              className="mt-4 w-full"
              onClick={() => router.push('/admin/products')}
            >
              Go to Products
            </Button>
          </CardContent>
        </Card>

        {/* Users Card */}
        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle>Users</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-gray-700">Manage user accounts, roles, and permissions.</p>
            <Button
              className="mt-4 w-full"
              onClick={() => router.push('/admin/user')}
            >
              Go to Users
            </Button>
          </CardContent>
        </Card>

        {/* Roles Card */}
        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle>Roles</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-gray-700">Manage roles of your users.</p>
            <Button
              className="mt-4 w-full"
              onClick={() => router.push('/admin/roles')}
            >
              Go to Roles
            </Button>
          </CardContent>
        </Card>

        {/* Transaction Card */}

        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle>Transactions</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-gray-700">Manage transactions of your users.</p>
            <Button
              className="mt-4 w-full"
              onClick={() => router.push('/admin/transaction')}
            >
              Go to Transactions
            </Button>
          </CardContent>
        </Card>

      </section>

      {/* Additional Info Section */}
      <section className="bg-gray-100 p-6 rounded-lg shadow-md">
        <h2 className="text-2xl font-bold mb-4">Tips & Announcements</h2>
        <ul className="list-disc list-inside space-y-2 text-gray-700">
          <li>Keep your categories and products up-to-date to enhance user experience.</li>
          <li>Check user feedback regularly to improve your platform.</li>
          <li>Use the analytics dashboard to track performance metrics.</li>
        </ul>
      </section>
    </div>
  );
};

export default AdminIntroPage;
