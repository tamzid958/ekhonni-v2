// This code is for practice purpose - Hafiz

'use client';

import React, { useState } from 'react';
import { useParams } from 'next/navigation';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import Image from 'next/image';

export default function ProductPage() {
  const { id } = useParams();
  const [images] = useState({
    img1: 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,b_rgb:f5f5f5/3396ee3c-08cc-4ada-baa9-655af12e3120/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img2: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/e44d151a-e27a-4f7b-8650-68bc2e8cd37e/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img3: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/44fc74b6-0553-4eef-a0cc-db4f815c9450/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img4: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/d3eb254d-0901-4158-956a-4610180545e5/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
  });

  const selectedImage = images[`img${id}`] || images.img1;

  return (
    <AspectRatio ratio={16 / 9} className="bg-muted">
      <Image
        src={selectedImage}
        alt={`Image for product ${id}`}
        fill
        className="h-full w-full rounded-md object-cover"
      />
    </AspectRatio>
  );
}
