'use client';

import React from 'react';
import useSWR from 'swr';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import Loading from '@/components/Loading';
import Error from '@/components/Error';
import process from 'next/dist/build/webpack/loaders/resolve-url-loader/lib/postcss';

interface UnsplashPhoto {
  id: string;
  urls: {
    regular: string;
    thumb: string;
  };
  alt_description: string;
}

const fetcher = async (url: string): Promise<UnsplashPhoto[]> => {
  const res = await fetch(url, {
    headers: {
      Authorization: `Client-ID ${process.env.NEXT_PUBLIC_UNSPLASH_CLIENT_ID}`,
    },
  });

  if (!res.ok) {
    throw new Error('Failed to fetch data');
  }
  return res.json();
};

const ProductsPage: React.FC = () => {
  const router = useRouter();
  const { data, error, isLoading } = useSWR<UnsplashPhoto[]>(
    'https://api.unsplash.com/photos?per_page=20',
    fetcher,
  );

  if (isLoading) return <Loading />;
  if (error) return <Error message="Failed to load product details." />;

  return (
    <div style={{ padding: '20px' }}>
      <h1>All Products</h1>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
        {data?.map((photo) => (
          <div
            key={photo.id}
            style={{
              border: '1px solid #ccc',
              borderRadius: '10px',
              padding: '10px',
              maxWidth: '200px',
            }}
          >
            <Image
              src={photo.urls.thumb}
              alt={photo.alt_description || 'Unsplash Photo'}
              width={150}
              height={150}
              style={{ borderRadius: '8px' }}
            />
            <h2 style={{ fontSize: '1rem', marginTop: '10px' }}>
              {photo.alt_description || 'No description available'}
            </h2>
            <button
              onClick={() => router.push(`/products/${photo.id}`)}
              style={{
                marginTop: '10px',
                padding: '5px 10px',
                backgroundColor: '#0070f3',
                color: '#fff',
                border: 'none',
                borderRadius: '5px',
                cursor: 'pointer',
              }}
            >
              View Details
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsPage;
