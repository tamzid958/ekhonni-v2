/** @type {import('next').NextConfig} */
const nextConfig = {
  webpack(config, { isServer }) {
    // If you need to customize the Webpack configuration, do so here
    return config;
  },
  reactStrictMode: true,
  images: {
    domains: [
      'via.placeholder.com',
      'jsonplaceholder.typicode.com',
      'images.unsplash.com',
      'static.nike.com',
      'picsum.photos',
      'lh3.googleusercontent.com',
      'res.cloudinary.com',
    ],
  },

};

export default nextConfig;
