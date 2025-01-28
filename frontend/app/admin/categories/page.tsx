import React from 'react';
import { fetchCategories } from './fetchCategories';
import CategoryRender from './CategoryRender';

interface Props {
  searchParams: { category?: string };
}

export default async function Page({ searchParams }: Props) {
  const category = searchParams?.category || 'All';
  const categories = await fetchCategories(category);

  return (
    <div>
      {/* Pass the fetched categories as props to the client component */}
      <CategoryRender category={category} categories={categories} />
    </div>
  );
}
