import React from 'react';
import CategoryActions from './CategoryActions';
import CategoryFilters from './CategoryFilters';
import AllCategories from './categories-table';

export default function Categories() {
  return (
    <section>
      <h1>Categories</h1>

      <CategoryActions />
      <CategoryFilters />
      <AllCategories />
    </section>
  );
}