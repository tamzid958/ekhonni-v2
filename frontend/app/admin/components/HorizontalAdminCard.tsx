'use client';

import { Separator } from '@/components/ui/separator';
import { Card, CardContent, CardDescription, CardFooter, CardTitle } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import Image from 'next/image';
import { Button } from '@/components/ui/button';
import * as React from 'react';
import { Badge } from '@/components/ui/badge';
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


interface WatchlistItem {
  id: number;
  title: string;
  img: string;
  price: number;
  yourBid?: number;
  shipping: number;
  timeLeft: string;
  condition: string;
  bidsAmount: number;
  sellerProfile: string;
  status: string;
}

async function approveProduct(id: number) {
  const url = `http://localhost:8080/api/v2/admin/product/pending/${id}/approve`;

  try {
    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        // Authorization: `Bearer YOUR_TOKEN_HERE`, // Include token if authentication is required
      },
      body: JSON.stringify({ reason: 'Approved by admin' }), // Add body if required by the API
    });

    if (!response.ok) {
      throw new Error(`Failed to approve the product with id ${id}. Status: ${response.status}`);
    }

    const data = await response.json();
    console.log(response);
    console.log(`Product with id ${id} successfully approved`, data);
    return data; // Return data if needed
  } catch (error) {
    console.error('Error in approve function:', error);
  }
}

async function declineProduct(id: number) {
  const url = `http://localhost:8080/api/v2/admin/product/pending/${id}/decline`;

  try {
    const response = await fetch(url, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        // Authorization: `Bearer YOUR_TOKEN_HERE`, // Include token if authentication is required
      },
      body: JSON.stringify({ reason: 'Declined by admin' }), // Add body if required by the API
    });

    console.log('Request URL:', url);
    console.log('Request Body:', JSON.stringify({ reason: 'Approved by admin' }));

    if (!response.ok) {
      throw new Error(`Failed to decline the product with id ${id}. Status: ${response.status}`);
    }

    const data = await response.json();
    console.log(response);
    console.log(`Product with id ${id} successfully declined`, data);
    return data; // Return data if needed
  } catch (error) {
    console.error('Error in approve function:', error);
  }
}

async function deleteProduct(id: number) {
  const url = `http://localhost:8080/api/v2/admin/product/approve/${id}`;

  try {
    const response = await fetch(url, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        // Authorization
      },
      body: JSON.stringify({ reason: 'Deleted by admin' }),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete the product with id ${id}. Status: ${response.status}`);
    }
    const data = await response.json();
    console.log(response);
    console.log(`Product with id ${id} successfully deleted`, data);
    return data;
  } catch (error) {
    console.error('Error in delete function:', error);
  }
}


export const HorizontalAdminCard: React.FC<WatchlistItem> = ({
                                                               id,
                                                               title,
                                                               img,
                                                               price,
                                                               yourBid,
                                                               shipping,
                                                               timeLeft,
                                                               condition,
                                                               bidsAmount,
                                                               sellerProfile,
                                                               status,
                                                             }) => {

  console.log(status);

  const handleApprove = async () => {
    // const confirmed = window.confirm('Are you sure you want to approve this product?');
    const confirmed = true;
    if (confirmed) {
      const result = await approveProduct(id); // revalidate path
      if (result) {
        // alert(`Product with ID ${id} successfully approved.`);
        window.location.reload();
        alert(`Product with ID ${id} successfully approved.`);

      } else {
        alert('Failed to approve the product.');
      }
    }
  };

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'APPROVED':
        return <Badge variant="default">Approved</Badge>;
      case 'DECLINED':
        return <Badge variant="destructive">Declined</Badge>;
      case 'PENDING_APPROVAL':
        return <Badge variant="secondary">Pending Approval</Badge>;
      case 'ARCHIVED':
        return <Badge variant="warning">Archived</Badge>;
      default:
        return <Badge variant="default">Default</Badge>;
    }
  };

  const handleDecline = async () => {
    // const confirmed = window.confirm('Are you sure you want to decline this product?');
    const confirmed = true;
    if (confirmed) {
      const result = await declineProduct(id);
      if (result) {
        alert(`Product with ID ${id} successfully declined.`);
        window.location.reload();
      } else {
        alert('Failed to decline the product.');
      }
    }
  };

  const handleDelete = async () => {
    // const confirmed = window.confirm('Are you sure you want to delete this product?');
    const confirmed = true;
    if (confirmed) {
      const result = await deleteProduct(id);
      if (result) {
        alert(`Product with ID ${id} successfully deleted.`);
        window.location.reload();
      } else {
        alert('Failed to delete the product.');
      }
    }
  };

  /*<Button variant="link" onClick={() => {*/
  return (
    <Card className="flex items-center gap-4 mb-4 w-full">
      {/* Checkbox */}
      <div className="ml-4">
        <Checkbox />
      </div>

      {/* Product Image */}
      <CardContent className="w-24 h-24 relative">
        <Image
          src={img}
          alt={title}
          layout="fill"
          className="rounded-md object-cover"
        />
      </CardContent>

      {/* Product Details */}
      <CardContent className="p-3 flex-1 flex flex-col space-y-2">
        {/* Title */}
        <CardContent className="p-6 text-gray-600">
          <div className="inline-flex">
            <CardTitle>{title}</CardTitle>
            <span className="px-4">{getStatusBadge(status)}</span>
          </div>
          <CardDescription className="pt-2">Condition: {condition}</CardDescription>
        </CardContent>
        {/* Details */}
        <ul className="flex flex-wrap px-6 md:justify-start text-sm gap-4 sm:justify-start">
          <li>
            <div className="pr-2">
              <p className="text-gray-600">Bids: {bidsAmount}</p>
              <CardTitle className="py-0.5">US ${price}</CardTitle>
            </div>
          </li>
          <li>
            <Separator orientation="vertical" className="h-full bg-gray-300" />
          </li>


          <li>
            <div className="">
              <p className="text-gray-600">TIME ENDS:</p>
              <p className="text-lg font-bold text-red-500">{timeLeft}</p>
            </div>
          </li>
          <li>
            <Separator orientation="vertical" className="h-full bg-gray-300" />
          </li>
          <li>
            <div className="">
              <p>Seller:</p>
              <p className="text-lg font-bold text-gray-800 py-1">{sellerProfile}</p>
            </div>
          </li>
        </ul>
      </CardContent>
      <CardFooter>
        <div className="flex flex-col mt-10 space-y-2">
          {status === 'PENDING_APPROVAL' && <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="default">Approve</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Are you sure you want to approve this product? </AlertDialogTitle>
                <AlertDialogDescription>
                  Once approved, it will be visible on the website for customers to view and
                  purchase. </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction onClick={() => {
                  handleApprove();
                }}>Continue</AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>}

          {status === 'PENDING_APPROVAL' && <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="destructive">Decline</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Are you sure you want to decline this product?</AlertDialogTitle>
                <AlertDialogDescription>
                  Declining it will remove it from the approval queue and prevent it from being displayed to customers.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction onClick={() => {
                  handleDecline();
                }}>Continue</AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>}
          {/*<Button variant="default" onClick={() => {*/}
          {/*  handleApprove();*/}
          {/*}}>Approve</Button>*/}
          {/*<Button variant="destructive" onClick={() => {*/}
          {/*  handleDecline();*/}
          {/*}}>Decline</Button>*/}

          {status === 'APPROVED' && <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="secondary" className="w-full bg-yellow-400 hover:bg-yellow-300">Delete</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Are you sure you want to delete this product?</AlertDialogTitle>
                <AlertDialogDescription>
                  Declining it will remove it from the approval queue and put it on archive.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction onClick={() => {
                  handleDelete();
                }}>Continue</AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>}

          <div className="relative group">
            <Button variant="link">More Action</Button>

            {/* Dropdown Menu */}
            <div
              className="absolute mt-2 bg-white border justify-items-center shadow-lg rounded-md w-32 z-10 opacity-0 group-focus-within:opacity-100 group-hover:opacity-100 transition-opacity duration-200">
              <Button variant="link">Product Details</Button>

            </div>
          </div>
        </div>
      </CardFooter>
    </Card>
  );
};
