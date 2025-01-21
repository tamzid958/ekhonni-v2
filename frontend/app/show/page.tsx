'use client';
import React, { useEffect, useState } from 'react';
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
// import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

export default function Show() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/v2/category/all');
        if (!response.ok) {
          throw new Error('Failed to fetch categories');
        }
        const data = await response.json();
        setCategories(data.data); // Extract 'data' from the response
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="p-6 max-w-lg mx-auto bg-white rounded-md">
      <h1 className="font-semibold text-2xl mb-4">Category Tree</h1>
      <Select>
        <SelectTrigger>
          <SelectValue placeholder={`Select your category`} />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            {/*<SelectLabel>Fruits</SelectLabel>*/}
            <SelectItem value="apple">Apple</SelectItem>
            <SelectItem value="banana">Banana</SelectItem>
            <SelectItem value="blueberry">Blueberry</SelectItem>
            <SelectItem value="grapes">Grapes</SelectItem>
            <SelectItem value="pineapple">Pineapple</SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
      {/*<ul className="space-y-4">*/}
      {/*  {categories.map((category) => (*/}
      {/*    <li key={category.name}>*/}
      {/*      <span className="font-bold text-lg">{category.name}</span>*/}
      {/*      {category.subCategories && category.subCategories.length > 0 && (*/}
      {/*        <ul className="pl-6 mt-2 space-y-1">*/}
      {/*          {category.subCategories.map((subCategory) => (*/}
      {/*            <li key={subCategory} className="text-gray-600 text-sm">*/}
      {/*              - {subCategory}*/}
      {/*            </li>*/}
      {/*          ))}*/}
      {/*        </ul>*/}
      {/*      )}*/}
      {/*    </li>*/}
      {/*  ))}*/}
      {/*</ul>*/}
    </div>
  );
}
