import React from 'react';
import { createColumnHelper } from '@tanstack/react-table';
import { useRouter } from 'next/navigation';
import { ProductData } from './page';
import { Button } from '@/components/ui/button';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog';
import { toast } from 'sonner';
import { Check, Trash, X } from 'lucide-react';
import { Badge } from '@/components/ui/badge';

const columnHelper = createColumnHelper<ProductData>();

export function getColumns(token: string, router: ReturnType<typeof useRouter>) {

  return [
    columnHelper.accessor('images', {
      header: 'Image',
      cell: (info) => (
        <img src={info.getValue()?.[0]?.imagePath || '/placeholder.jpg'} alt="Image Not Found"
             className="w-32 h-32 object-cover rounded" />
      ),
    }),
    columnHelper.accessor('title', {
      header: 'Product Title',
      cell: (info) => (
        <span onClick={() => router.push(`/productDetails?id=${info.row.original.id}`)}
              className="text-black cursor-pointer hover:underline">
          {info.getValue()}
        </span>
      ),
    }),
    columnHelper.accessor('seller.name', {
      header: 'Seller Name',
      cell: (info) => (
        <span onClick={() => router.push(`/seller-page/${info.row.original.seller.id}`)}
              className="text-black cursor-pointer hover:underline">
          {info.getValue()}
        </span>
      ),
    }),
    columnHelper.accessor('price', {
      header: 'Price',
    }),
    columnHelper.accessor('createdAt', {
      header: 'Date',
      cell: (info) => new Date(info.getValue()).toLocaleString(),
    }),
    columnHelper.accessor('condition', {
      header: 'Condition',
      cell: (info) => info.getValue().replace('_', ' '),
    }),
    columnHelper.accessor('status', {
      header: 'Status',
      cell: (info) => getStatusBadge(info.getValue()),
    }),
    columnHelper.accessor('category.name', {
      header: 'Category Name',
    }),
    // columnHelper.accessor('description', {
    //   header: 'Description',
    // }),
    columnHelper.accessor('boostData.boostType', {
      header: 'Boost Data',
    }),
    columnHelper.display({
      id: 'actions',
      header: 'Actions',
      cell: (info) => {
        const product = info.row.original;
        return (
          <div className="space-y-2 flex flex-col">
            {product.status === 'PENDING_APPROVAL' && (
              <>
                <AlertDialog key={`approve-${product.id}`}>
                  <AlertDialogTrigger asChild>
                    <Button variant="secondary" className="text-green-500 hover:text-green-700">
                      <Check size={22} strokeWidth={5} />
                    </Button>
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Approve this product?</AlertDialogTitle>
                      <AlertDialogDescription>Once approved, it will be visible for customers.</AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction onClick={() => approveProduct(product.id)}>Approve</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>

                <AlertDialog key={`decline-${product.id}`}>
                  <AlertDialogTrigger asChild>
                    <Button variant="secondary" className="text-red-500 hover:text-red-700">
                      <X size={22} strokeWidth={5} />
                    </Button>
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Decline this product?</AlertDialogTitle>
                      <AlertDialogDescription>Declining will remove it from the approval queue.</AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction onClick={() => declineProduct(product.id)}>Decline</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </>
            )}
            {product.status === 'APPROVED' && (
              <AlertDialog key={`delete-${product.id}`}>
                <AlertDialogTrigger asChild>
                  <Button variant="secondary" className="text-gray-500 hover:text-gray-700">
                    <Trash size={22} strokeWidth={5} />
                  </Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>Delete this product?</AlertDialogTitle>
                    <AlertDialogDescription>Deleting will remove it from the system.</AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                    <AlertDialogAction onClick={() => deleteProduct(product.id)}>Delete</AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
            )}
          </div>
        );
      },
    }),
  ];
}

const getStatusBadge = (status: string) => {
  const badgeMap = {
    APPROVED: <Badge variant="default">Approved</Badge>,
    DECLINED: <Badge variant="destructive">Declined</Badge>,
    PENDING_APPROVAL: <Badge variant="secondary" className="text-center">Pending</Badge>,
    ARCHIVED: <Badge variant="outline">Archived</Badge>,
    SOLD: <Badge variant="default">Sold</Badge>,
  };

  return badgeMap[status] || <Badge variant="default">Default</Badge>;
};

const approveProduct = async (id: number) => {
  try {
    const response = await fetch(`http://localhost:8080/api/v2/admin/product/pending/${id}/approve`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ reason: 'Approved by admin' }),
    });
    if (!response.ok) throw new Error(`Failed to approve product with ID ${id}`);
    toast('Product approved successfully');
  } catch (error) {
    toast('Failed to approve product.');
  }
};

const declineProduct = async (id: number) => {
  try {
    const response = await fetch(`http://localhost:8080/api/v2/admin/product/pending/${id}/decline`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ reason: 'Declined by admin' }),
    });
    if (!response.ok) throw new Error(`Failed to decline product with ID ${id}`);
    toast('Product declined successfully');
  } catch (error) {
    toast('Failed to decline product.');
  }
};

const deleteProduct = async (id: number) => {
  try {
    const response = await fetch(`http://localhost:8080/api/v2/admin/product/approve/${id}`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ reason: 'Deleted by admin' }),
    });
    if (!response.ok) throw new Error(`Failed to delete product with ID ${id}`);
    toast('Product deleted successfully');
  } catch (error) {
    toast('Failed to delete product.');
  }
};