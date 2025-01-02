const mockData = [
  {
    id: '1',
    title: 'Mountain View',
    description:
      'Experience the majesty of towering mountains with a view that takes your breath away. Perfect for nature lovers and those who seek tranquility in the great outdoors. A paradise for photographers and hikers alike.',
    img: 'https://via.placeholder.com/400',
    price: 100,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '2',
    title: 'Ocean Sunset',
    description:
      'Watch the sun dip below the horizon in a spectacular display of colors over the ocean. A serene experience that soothes the soul and invigorates the mind. A perfect end to any day.',
    img: 'https://via.placeholder.com/401',
    price: 200,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '3',
    title: 'Forest Path',
    description:
      'Take a peaceful walk through a tranquil forest path lined with lush greenery. Hear the soft rustling of leaves and the chirping of birds as you explore this hidden gem. An escape into nature’s beauty.',
    img: 'https://via.placeholder.com/402',
    price: 150,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '4',
    title: 'Vintage Clock',
    description:
      'A beautifully crafted antique clock that brings elegance and history to your living space. A timeless piece that stands the test of time.',
    img: 'https://via.placeholder.com/403',
    price: 120,
    category: 'Antiques',
    label: 'Best Selling',
  },
  {
    id: '5',
    title: 'Smartphone 2025',
    description:
      'Experience the future with the latest smartphone, featuring cutting-edge technology and stunning design. A must-have gadget for tech enthusiasts.',
    img: 'https://via.placeholder.com/404',
    price: 180,
    category: 'Electronics',
    label: 'Limited Time Deals',
  },
  {
    id: '6',
    title: 'City Lights',
    description:
      'Dive into the vibrant energy of city lights at night, illuminating the urban landscape with endless possibilities. A bustling hub of activity where dreams are made and adventures await.',
    img: 'https://via.placeholder.com/405',
    price: 250,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '7',
    title: 'Designer Vase',
    description:
      'A stunning designer vase to enhance the aesthetics of your living space. Perfect for showcasing your favorite flowers or as a standalone piece.',
    img: 'https://via.placeholder.com/406',
    price: 130,
    category: 'Home Decor',
    label: 'Best Selling',
  },
  {
    id: '8',
    title: 'Tropical Paradise',
    description:
      'Indulge in the breathtaking beauty of a tropical beach with golden sands and turquoise waters. The ultimate destination for unwinding, basking in the sun, and creating unforgettable memories.',
    img: 'https://via.placeholder.com/407',
    price: 300,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '9',
    title: 'Bluetooth Speaker',
    description:
      'A compact yet powerful Bluetooth speaker with exceptional sound quality. Ideal for parties, gatherings, or personal use.',
    img: 'https://via.placeholder.com/408',
    price: 140,
    category: 'Electronics',
    label: 'Top Rated',
  },
  {
    id: '10',
    title: 'Golden Fields',
    description:
      'Stroll through golden fields at dusk, where the setting sun casts a warm glow. A rustic charm that inspires creativity and offers a glimpse of nature’s quiet splendor.',
    img: 'https://via.placeholder.com/409',
    price: 110,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '11',
    title: 'Antique Lamp',
    description:
      'A rare antique lamp that combines elegance and functionality. Adds a vintage charm to your space while providing a warm glow.',
    img: 'https://via.placeholder.com/410',
    price: 190,
    category: 'Antiques',
    label: 'Limited Time Deals',
  },
  {
    id: '12',
    title: 'Hidden Waterfall',
    description:
      'Unveil the magic of a hidden waterfall nestled within a dense jungle. The cascading water and vibrant greenery create an enchanting scene that feels like stepping into a dream.',
    img: 'https://via.placeholder.com/411',
    price: 220,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '13',
    title: 'Rolling Hills',
    description:
      'Gaze upon gentle rolling hills that stretch into the horizon, blending perfectly with the sky. A soothing vista that invites you to pause and appreciate life’s simple pleasures.',
    img: 'https://via.placeholder.com/412',
    price: 105,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '14',
    title: 'Autumn Forest',
    description:
      'Step into a vibrant forest in autumn, where leaves turn shades of orange and red. A seasonal masterpiece that celebrates nature’s ever-changing beauty. A feast for the eyes and soul.',
    img: 'https://via.placeholder.com/413',
    price: 175,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '15',
    title: 'Starry Sky',
    description:
      'Witness a clear sky full of stars, where the cosmos reveals its infinite wonders. A celestial spectacle that inspires curiosity and reminds us of the vastness of the universe.',
    img: 'https://via.placeholder.com/414',
    price: 210,
    category: 'Travel & Nature',
    label: 'Top Rated',
  }, {
    id: '16',
    title: 'Mountain View',
    description:
      'Experience the majesty of towering mountains with a view that takes your breath away. Perfect for nature lovers and those who seek tranquility in the great outdoors. A paradise for photographers and hikers alike.',
    img: 'https://via.placeholder.com/400',
    price: 100,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '17',
    title: 'Ocean Sunset',
    description:
      'Watch the sun dip below the horizon in a spectacular display of colors over the ocean. A serene experience that soothes the soul and invigorates the mind. A perfect end to any day.',
    img: 'https://via.placeholder.com/401',
    price: 200,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '18',
    title: 'Forest Path',
    description:
      'Take a peaceful walk through a tranquil forest path lined with lush greenery. Hear the soft rustling of leaves and the chirping of birds as you explore this hidden gem. An escape into nature’s beauty.',
    img: 'https://via.placeholder.com/402',
    price: 150,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '19',
    title: 'Vintage Clock',
    description:
      'A beautifully crafted antique clock that brings elegance and history to your living space. A timeless piece that stands the test of time.',
    img: 'https://via.placeholder.com/403',
    price: 120,
    category: 'Antiques',
    label: 'Best Selling',
  },
  {
    id: '20',
    title: 'Smartphone 2025',
    description:
      'Experience the future with the latest smartphone, featuring cutting-edge technology and stunning design. A must-have gadget for tech enthusiasts.',
    img: 'https://via.placeholder.com/404',
    price: 180,
    category: 'Electronics',
    label: 'Limited Time Deals',
  },
  {
    id: '21',
    title: 'City Lights',
    description:
      'Dive into the vibrant energy of city lights at night, illuminating the urban landscape with endless possibilities. A bustling hub of activity where dreams are made and adventures await.',
    img: 'https://via.placeholder.com/405',
    price: 250,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '22',
    title: 'Designer Vase',
    description:
      'A stunning designer vase to enhance the aesthetics of your living space. Perfect for showcasing your favorite flowers or as a standalone piece.',
    img: 'https://via.placeholder.com/406',
    price: 130,
    category: 'Home Decor',
    label: 'Best Selling',
  },
  {
    id: '23',
    title: 'Tropical Paradise',
    description:
      'Indulge in the breathtaking beauty of a tropical beach with golden sands and turquoise waters. The ultimate destination for unwinding, basking in the sun, and creating unforgettable memories.',
    img: 'https://via.placeholder.com/407',
    price: 300,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '24',
    title: 'Bluetooth Speaker',
    description:
      'A compact yet powerful Bluetooth speaker with exceptional sound quality. Ideal for parties, gatherings, or personal use.',
    img: 'https://via.placeholder.com/408',
    price: 140,
    category: 'Electronics',
    label: 'Top Rated',
  },
  {
    id: '25',
    title: 'Golden Fields',
    description:
      'Stroll through golden fields at dusk, where the setting sun casts a warm glow. A rustic charm that inspires creativity and offers a glimpse of nature’s quiet splendor.',
    img: 'https://via.placeholder.com/409',
    price: 110,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
];

export const fetchAllProducts = (): typeof mockData => {
  return mockData;
};

// Fetch products by category
export const fetchProductsByCategory = (category: string): typeof mockData => {
  if (category === 'All') {
    return mockData;
  }
  return mockData.filter((product) => product.category === category);
};

// Fetch products by label
export const fetchProductsByLabel = (label: string): typeof mockData => {
  if (label === 'All') {
    return mockData;
  }
  return mockData.filter((product) => product.label === label);
};


// Fetch product by ID
export const fetchProductById = (id: string): typeof mockData[0] | null => {
  return mockData.find((product) => product.id === id) || null;
};