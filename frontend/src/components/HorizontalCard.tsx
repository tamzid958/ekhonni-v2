// import { Separator } from '@/components/ui/separator';
// import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
// import { Checkbox } from '@/components/ui/checkbox';
// import Image from 'next/image';
// import { Button } from '@/components/ui/button';
// import * as React from 'react';
//
// // Data format
// interface Seller {
//   id: string;
//   name: string;
// }
//
// interface ImageType {
//   imagePath: string;
// }
//
// interface Category {
//   id: number;
//   name: string;
// }
//
// interface WatchlistItem {
//   id: number;
//   title: string;
//   subTitle: string;
//   price: number;
//   condition: string;
//   seller: Seller;
//   category: Category;
//   images: ImageType[];
// }
//
// // Component Props
// interface HorizontalCardProps {
//   watchlistItem: WatchlistItem;
// }
//
// export const HorizontalCard: React.FC<HorizontalCardProps> = ({ watchlistItem }) => {
//   const { title, subTitle, price, condition, seller, images } = watchlistItem;
//
//   return (
//     <Card
//       className="flex items-center justify-between p-4 bg-white shadow-lg rounded-xl w-full max-w-4xl border border-gray-200">
//       {/* Left Side - Checkbox & Image */}
//       <div className="flex items-center gap-3">
//         {/* Checkbox */}
//         <Checkbox className="mr-2" />
//
//         {/* Product Image */}
//         <div className="relative w-24 h-24">
//           <Image
//             src={images[0]?.imagePath || '/default.jpg'}
//             alt={title}
//             layout="fill"
//             className="rounded-md object-cover border"
//           />
//         </div>
//       </div>
//
//       {/* Middle Section - Product Details */}
//       <CardContent className="flex-1 flex flex-col gap-1 px-4">
//         <CardTitle className="text-lg font-semibold text-gray-900">{title}</CardTitle>
//         <p className="text-sm text-gray-600">{subTitle}</p>
//         <p className="text-sm text-gray-500">Condition: <span className="font-medium">{condition}</span></p>
//
//         {/* Price & Seller Info */}
//         <div className="flex items-center gap-3 mt-2">
//           <span className="text-sm text-gray-600">Price:</span>
//           <span className="text-xl font-bold text-gray-900">US ${price.toFixed(2)}</span>
//           <Separator orientation="vertical" className="h-5 bg-gray-300" />
//           <span className="text-sm text-gray-700">Seller: <span className="font-medium">{seller.name}</span></span>
//         </div>
//       </CardContent>
//
//       {/* Right Side - Buttons */}
//       <CardFooter className="flex flex-col gap-2">
//         <Button className="w-40">Bid Now</Button>
//         <Button variant="secondary" className="w-40">View Seller’s Items</Button>
//         <Button variant="outline" className="w-40">More Actions</Button>
//       </CardFooter>
//     </Card>
//   );
// };


import { Separator } from '@/components/ui/separator';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import Image from 'next/image';
import { Button } from '@/components/ui/button';
import * as React from 'react';
import Link from 'next/link';
import { toast } from 'sonner';

// Data format
interface Seller {
  id: string;
  name: string;
}

interface ImageType {
  imagePath: string;
}

interface Category {
  id: number;
  name: string;
}

interface WatchlistItem {
  id: number;
  title: string;
  subTitle: string;
  price: number;
  condition: string;
  seller: Seller;
  category: Category;
  images: ImageType[];
}

// Component Props
interface HorizontalCardProps {
  watchlistItem: WatchlistItem;
  token: string;
}

export const HorizontalCard: React.FC<HorizontalCardProps> = ({ watchlistItem, token }) => {
  const { title, subTitle, price, condition, seller, images } = watchlistItem;

  async function handleRemove(id: number) {
    try {
      const response = await fetch('http://localhost:8080/api/v2/user/watchlist', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify([id]),
      });

      const responseData = await response.json();
      if (response.ok && responseData.success) {
        toast.success('Removed from wishlist.');
      } else {
        toast.error(responseData.message || 'Failed to remove from wishlist.');
      }
    } catch (error) {
      console.error('Error removing from wishlist:', error);
      toast.error('An error occurred while removing from wishlist.');
    }
  }

  return (
    <Card
      className="flex items-center justify-between p-4 bg-white shadow-md rounded-xl w-full max-w-6xl border border-gray-200 hover:shadow-lg transition-shadow duration-200">
      {/* Left Side - Checkbox & Image */}
      <div className="flex items-center gap-4">
        {/* Checkbox */}
        <Checkbox className="mr-2" />

        {/* Product Image */}
        <div className="relative w-28 h-28 border border-gray-300 rounded-lg overflow-hidden">
          <Image
            src={images[0]?.imagePath || '/default.jpg'}
            alt={title}
            layout="fill"
            className="object-cover"
          />
        </div>
      </div>

      {/* Middle Section - Product Details */}
      <CardContent className="flex-1 flex flex-col gap-1 px-4">
        <CardTitle className="text-lg font-semibold text-gray-900">{title}</CardTitle>
        {/*<p className="text-sm text-gray-600">{subTitle}</p>*/}
        <p className="text-sm text-gray-500">Condition: <span className="font-medium">{condition}</span></p>

        {/* Price & Seller Info */}
        <div className="flex items-center gap-6 mt-2">
          <div className="flex flex-col">
            <span className="text-sm text-gray-600">Price:</span>
            <span className="text-xl font-bold text-gray-900">US ${price.toFixed(2)}</span>
          </div>
          <Separator orientation="vertical" className="h-10 gap-5 bg-gray-600" />
          <div className="flex flex-col items-start">
            <span className="text-sm text-gray-700">Seller:</span>
            <Link
              href={`/seller-page/${watchlistItem.seller.id}`}
              className="font-medium text-gray-800 hover:text-blue-600 hover:font-bold hover:underline transition-colors duration-200"
            >
              {seller.name}
            </Link>
          </div>
        </div>
      </CardContent>

      {/* Right Side - Buttons & Call-to-Actions */}
      <CardFooter className="flex flex-col items-end gap-2 p-2">
        {/* Bid Now Button */}
        <Link href={`/productDetails?id=${watchlistItem.id}`} passHref>
          <Button className="w-44 text-sm font-medium">Bid Now</Button>
        </Link>

        {/* View Seller Profile Button */}
        <Link href={`/seller-page/${watchlistItem.seller.id}`} passHref>
          <Button variant="secondary" className="w-44 text-sm font-medium">View Seller Profile</Button>
        </Link>

        {/* Remove Button */}
        <Button
          variant="destructive"
          className="w-44 text-sm font-medium"
          onClick={() => handleRemove(watchlistItem.id)}
        >
          Remove
        </Button>
      </CardFooter>
    </Card>
  );
};
