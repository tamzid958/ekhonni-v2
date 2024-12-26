import { NextResponse } from 'next/server';

const mockData = [
  {
    id: '1',
    title: 'Mountain View',
    description: 'A stunning mountain view.',
    img: 'https://via.placeholder.com/400',
    price: 100,
    category: 'Best Selling',
  },
  {
    id: '2',
    title: 'Ocean Sunset',
    description: 'Beautiful ocean sunset.',
    img: 'https://via.placeholder.com/401',
    price: 200,
    category: 'Limited Time Deals',
  },
  {
    id: '3',
    title: 'Forest Path',
    description: 'A tranquil forest path.',
    img: 'https://via.placeholder.com/402',
    price: 150,
    category: 'Top Rated',
  },
];

// API route for dynamic path `id`
export async function GET(request: Request, { params }: { params: { id: string } }) {
  const { id } = params; // Get the `id` from the URL

  const product = mockData.find((item) => item.id === id);

  if (!product) {
    return NextResponse.json({ error: 'Product not found' }, { status: 404 });
  }

  return NextResponse.json(product);
}
