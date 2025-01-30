// app/show/page.js
import React from 'react';
import ShowClient from './ShowClient';

async function fetchCategories() {
  const res = await fetch('http://localhost:8080/api/v2/category/all');
  if (!res.ok) {
    throw new Error('Failed to fetch categories');
  }
  return res.json();
}

export default async function ShowPage() {
  const data = await fetchCategories();
  const categories = data.data;

  return <ShowClient categories={categories} />;
}