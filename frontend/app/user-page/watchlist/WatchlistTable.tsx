// components/watchlist/WatchlistTable.tsx
import React from 'react';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Button } from '@/components/ui/button';
import { Eye, Trash } from 'lucide-react';

interface WatchlistItem {
  id: number;
  title: string;
  price: number;
  condition: string;
  status: string;
  createdAt: string;
  seller: { name: string };
  category: { name: string };
  images: { imagePath: string }[];
}

interface Props {
  watchlistItems: WatchlistItem[];
}

const WatchlistTable: React.FC<Props> = ({ watchlistItems }) => {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Image</TableHead>
          <TableHead>Title</TableHead>
          <TableHead>Price</TableHead>
          <TableHead>Condition</TableHead>
          <TableHead>Seller</TableHead>
          <TableHead>Category</TableHead>
          <TableHead>Status</TableHead>
          {/*<TableHead>Created At</TableHead>*/}
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {watchlistItems.map((item) => (
          <TableRow key={item.id}>
            <TableCell>
              <img
                src={item.images[0]?.imagePath || '/placeholder.jpg'}
                alt={item.title}
                className="w-24 h-24 rounded-md"
              />
            </TableCell>
            <TableCell className="font-medium">{item.title}</TableCell>
            <TableCell>${item.price.toFixed(2)}</TableCell>
            <TableCell>{item.condition.replace('_', ' ')}</TableCell>
            <TableCell>{item.seller.name}</TableCell>
            <TableCell>{item.category.name}</TableCell>
            <TableCell>
              <span
                className={`px-2 py-1 rounded text-xs font-semibold ${
                  item.status === 'APPROVED' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'
                }`}
              >
                {item.status}
              </span>
            </TableCell>
            {/*<TableCell>{new Date(item.createdAt).toLocaleDateString()}</TableCell>*/}
            <TableCell>
              <div className="flex gap-2">
                <Button variant="outline" size="sm">
                  <Eye className="w-4 h-4" />
                </Button>
                <Button variant="destructive" size="sm">
                  <Trash className="w-4 h-4" />
                </Button>
              </div>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};

export default WatchlistTable;
