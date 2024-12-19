import React from 'react';
import Link from 'next/link';

const Navbar = () => {
  return (
    <nav className="flex justify-between p-4 bg-blue-600 text-white">
      <div className="font-bold"> Ekhonni.com </div>
      <div className="flex gap-4">
        <Link href="/" className="hover:text-blue-200">
          Home
        </Link>
        <Link href="/products" className="hover:text-blue-200">
          Products
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
