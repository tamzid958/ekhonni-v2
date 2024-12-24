/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: [
      'via.placeholder.com',
      'jsonplaceholder.typicode.com',
      'images.unsplash.com',
      'static.nike.com',
    ],
  },
};

export default nextConfig;
