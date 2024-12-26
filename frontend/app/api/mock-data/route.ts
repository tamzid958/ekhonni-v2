import { NextResponse } from 'next/server';

const mockData = [
  {
    'id': '1',
    'title': 'Mountain View',
    'description': 'A stunning mountain view.',
    'img': 'https://via.placeholder.com/400',
    'price': 100,
    'category': 'Best Selling',
  },
  {
    'id': '2',
    'title': 'Ocean Sunset',
    'description': 'Beautiful ocean sunset.',
    'img': 'https://via.placeholder.com/401',
    'price': 200,
    'category': 'Limited Time Deals',
  },
  {
    'id': '3',
    'title': 'Forest Path',
    'description': 'A tranquil forest path.',
    'img': 'https://via.placeholder.com/402',
    'price': 150,
    'category': 'Top Rated',
  },
];


export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);
  const id = searchParams.get('id');

  const product = mockData.find((item) => item.id === id);

  if (!product) {
    return NextResponse.json({ error: 'Product not found' }, { status: 404 });
  }

  return NextResponse.json(product);
}
