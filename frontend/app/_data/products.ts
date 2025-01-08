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
  {
    id: '26',
    title: 'Coastal Escape',
    description:
      'Discover the beauty of the coastline with pristine beaches, dramatic cliffs, and a peaceful atmosphere. Perfect for those seeking a tranquil retreat by the sea.',
    img: 'https://via.placeholder.com/415',
    price: 180,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '27',
    title: 'Mountain Trek',
    description:
      'Embark on an exhilarating trek through rugged mountains, with breathtaking views and challenging terrain. A journey for the adventurous at heart.',
    img: 'https://via.placeholder.com/416',
    price: 220,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '28',
    title: 'Seaside Cottage',
    description:
      'Stay in a cozy cottage by the sea, offering spectacular ocean views and a peaceful escape from the hustle and bustle of everyday life.',
    img: 'https://via.placeholder.com/417',
    price: 250,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '29',
    title: 'Luxury Watch',
    description:
      'A beautifully designed luxury watch, offering sophistication, precision, and timeless elegance. The perfect accessory for any occasion.',
    img: 'https://via.placeholder.com/418',
    price: 300,
    category: 'Accessories',
    label: 'Best Selling',
  },
  {
    id: '30',
    title: 'Smart Home Hub',
    description:
      'A smart home hub that allows you to control your entire home with a simple voice command. The future of home automation at your fingertips.',
    img: 'https://via.placeholder.com/419',
    price: 180,
    category: 'Electronics',
    label: 'Limited Time Deals',
  },
  {
    id: '31',
    title: 'Urban Jungle',
    description:
      'A lush indoor plant collection that brings a touch of nature into your home, creating a calming and refreshing environment.',
    img: 'https://via.placeholder.com/420',
    price: 100,
    category: 'Home Decor',
    label: 'Top Rated',
  },
  {
    id: '32',
    title: 'Antique Painting',
    description:
      'A rare and exquisite antique painting that adds historical charm and artistic beauty to any collection or home decor.',
    img: 'https://via.placeholder.com/421',
    price: 250,
    category: 'Antiques',
    label: 'Best Selling',
  },
  {
    id: '33',
    title: 'Digital Camera',
    description:
      'Capture stunning photos with this high-performance digital camera, perfect for both professionals and hobbyists.',
    img: 'https://via.placeholder.com/422',
    price: 350,
    category: 'Electronics',
    label: 'Limited Time Deals',
  },
  {
    id: '34',
    title: 'Elegant Necklace',
    description:
      'A beautifully crafted necklace made from precious materials, offering elegance and charm for any occasion.',
    img: 'https://via.placeholder.com/423',
    price: 120,
    category: 'Jewelry',
    label: 'Top Rated',
  },
  {
    id: '35',
    title: 'Winter Wonderland',
    description:
      'Experience the magic of winter with snow-capped mountains, serene landscapes, and festive holiday cheer. The ultimate winter getaway.',
    img: 'https://via.placeholder.com/424',
    price: 160,
    category: 'Travel & Nature',
    label: 'Best Selling',
  },
  {
    id: '36',
    title: 'Designer Sofa',
    description:
      'A chic and comfortable designer sofa that complements any modern living room, offering style and relaxation.',
    img: 'https://via.placeholder.com/425',
    price: 500,
    category: 'Home Decor',
    label: 'Limited Time Deals',
  },
  {
    id: '37',
    title: 'Gourmet Coffee Set',
    description:
      'Indulge in the finest coffee blends with this gourmet coffee set. Perfect for coffee lovers who appreciate high-quality beans and expert brewing.',
    img: 'https://via.placeholder.com/426',
    price: 90,
    category: 'Food & Drink',
    label: 'Top Rated',
  },
  {
    id: '38',
    title: 'Vintage Car',
    description:
      'Own a piece of automotive history with this classic vintage car. A collector’s dream, featuring elegant design and powerful performance.',
    img: 'https://via.placeholder.com/427',
    price: 35000,
    category: 'Automobiles',
    label: 'Best Selling',
  },
  {
    id: '39',
    title: 'Outdoor Adventure Gear',
    description:
      'Everything you need for the perfect outdoor adventure, including tents, sleeping bags, and backpacks designed for comfort and durability.',
    img: 'https://via.placeholder.com/428',
    price: 150,
    category: 'Sports & Outdoors',
    label: 'Limited Time Deals',
  },
  {
    id: '40',
    title: 'Yoga Mat',
    description:
      'A high-quality yoga mat designed for comfort and stability during your practice, available in various colors.',
    img: 'https://via.placeholder.com/429',
    price: 45,
    category: 'Fitness',
    label: 'Top Rated',
  },
  {
    id: '41',
    title: 'Classic Piano',
    description:
      'A grand piano that combines classical design with exceptional sound, perfect for music lovers and professional musicians alike.',
    img: 'https://via.placeholder.com/430',
    price: 5000,
    category: 'Music Instruments',
    label: 'Best Selling',
  },
  {
    id: '42',
    title: 'Smart Fitness Tracker',
    description:
      'Monitor your health and fitness goals with this smart fitness tracker, offering advanced features and a sleek design.',
    img: 'https://via.placeholder.com/431',
    price: 120,
    category: 'Electronics',
    label: 'Limited Time Deals',
  },
  {
    id: '43',
    title: 'Bohemian Rug',
    description:
      'A vibrant and unique bohemian rug that adds character and warmth to your living space, made from high-quality materials.',
    img: 'https://via.placeholder.com/432',
    price: 180,
    category: 'Home Decor',
    label: 'Top Rated',
  },
  {
    id: '44',
    title: 'Smart Glasses',
    description:
      'A pair of stylish smart glasses that combine fashion and technology, offering hands-free control and augmented reality features.',
    img: 'https://via.placeholder.com/433',
    price: 300,
    category: 'Electronics',
    label: 'Best Selling',
  },
  {
    id: '45',
    title: 'Ocean Breeze',
    description:
      'Breathe in the refreshing ocean breeze while enjoying a relaxing beach getaway. A perfect destination for unwinding and rejuvenation.',
    img: 'https://via.placeholder.com/434',
    price: 220,
    category: 'Travel & Nature',
    label: 'Limited Time Deals',
  },
  {
    id: '46',
    title: 'Crystal Glassware',
    description:
      'Exquisite crystal glassware that adds elegance to your dining table. Perfect for special occasions or as a gift for loved ones.',
    img: 'https://via.placeholder.com/435',
    price: 150,
    category: 'Home Decor',
    label: 'Top Rated',
  },
  {
    id: '47',
    title: 'Electric Guitar',
    description:
      'A high-quality electric guitar that offers exceptional sound and playability, ideal for both beginners and seasoned musicians.',
    img: 'https://via.placeholder.com/436',
    price: 250,
    category: 'Music Instruments',
    label: 'Best Selling',
  },
  {
    id: '48',
    title: 'Luxury Handbag',
    description:
      'A luxurious handbag made from premium materials, offering both style and functionality. A timeless accessory for any fashion-forward individual.',
    img: 'https://via.placeholder.com/437',
    price: 600,
    category: 'Fashion',
    label: 'Limited Time Deals',
  },
  {
    id: '49',
    title: 'Sailing Adventure',
    description:
      'Set sail on a thrilling adventure across the open sea, exploring new destinations and experiencing the beauty of the ocean.',
    img: 'https://via.placeholder.com/438',
    price: 300,
    category: 'Travel & Nature',
    label: 'Top Rated',
  },
  {
    id: '50',
    title: 'Gourmet Chocolate Box',
    description:
      'Indulge in an assortment of the finest gourmet chocolates, carefully curated to provide an exceptional tasting experience.',
    img: 'https://via.placeholder.com/439',
    price: 80,
    category: 'Food & Drink',
    label: 'Best Selling',
  },
];

export const fetchAllProducts = (): typeof mockData => {
  return mockData;
};

// Fetch products by categoryProducts
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