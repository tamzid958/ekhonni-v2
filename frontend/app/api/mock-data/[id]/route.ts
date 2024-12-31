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
<<<<<<< HEAD
  {
    id: '4',
    title: 'Desert Oasis',
    description: 'A serene desert oasis.',
    img: 'https://via.placeholder.com/403',
    price: 120,
    category: 'Best Selling',
  },
  {
    id: '5',
    title: 'Snowy Peaks',
    description: 'Majestic snowy peaks.',
    img: 'https://via.placeholder.com/404',
    price: 180,
    category: 'Limited Time Deals',
  },
  {
    id: '6',
    title: 'City Lights',
    description: 'Vibrant city lights at night.',
    img: 'https://via.placeholder.com/405',
    price: 250,
    category: 'Top Rated',
  },
  {
    id: '7',
    title: 'Lush Meadows',
    description: 'Expansive lush meadows.',
    img: 'https://via.placeholder.com/406',
    price: 130,
    category: 'Best Selling',
  },
  {
    id: '8',
    title: 'Tropical Paradise',
    description: 'A breathtaking tropical beach.',
    img: 'https://via.placeholder.com/407',
    price: 300,
    category: 'Limited Time Deals',
  },
  {
    id: '9',
    title: 'Quiet Lake',
    description: 'A peaceful lakeside view.',
    img: 'https://via.placeholder.com/408',
    price: 140,
    category: 'Top Rated',
  },
  {
    id: '10',
    title: 'Golden Fields',
    description: 'Fields of golden crops at dusk.',
    img: 'https://via.placeholder.com/409',
    price: 110,
    category: 'Best Selling',
  },
  {
    id: '11',
    title: 'Rocky Coastline',
    description: 'Rugged coastline with waves.',
    img: 'https://via.placeholder.com/410',
    price: 190,
    category: 'Limited Time Deals',
  },
  {
    id: '12',
    title: 'Hidden Waterfall',
    description: 'A hidden waterfall in the jungle.',
    img: 'https://via.placeholder.com/411',
    price: 220,
    category: 'Top Rated',
  },
  {
    id: '13',
    title: 'Rolling Hills',
    description: 'Gentle hills stretching into the horizon.',
    img: 'https://via.placeholder.com/412',
    price: 105,
    category: 'Best Selling',
  },
  {
    id: '14',
    title: 'Autumn Forest',
    description: 'A vibrant forest in autumn.',
    img: 'https://via.placeholder.com/413',
    price: 175,
    category: 'Limited Time Deals',
  },
  {
    id: '15',
    title: 'Starry Sky',
    description: 'A clear sky full of stars.',
    img: 'https://via.placeholder.com/414',
    price: 210,
    category: 'Top Rated',
  },
];

export default mockData;

=======
];

>>>>>>> 1e361d2d9968b94fa43ebcfdc5c31fc57d44facb
// API route for dynamic path `id`
export async function GET(request: Request, { params }: { params: { id: string } }) {
  const { id } = params; // Get the `id` from the URL

  const product = mockData.find((item) => item.id === id);

  if (!product) {
    return NextResponse.json({ error: 'Product not found' }, { status: 404 });
  }

  return NextResponse.json(product);
}
