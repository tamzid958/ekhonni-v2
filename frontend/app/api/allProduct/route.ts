export async function GET(req: Request) {

  const allProducts = [
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


  return new Response(JSON.stringify(allProducts), {
    status: 200,
    headers: {
      'Content-Type': 'application/json',
    },
  });
}