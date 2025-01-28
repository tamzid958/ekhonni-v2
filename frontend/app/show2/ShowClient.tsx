// app/show/ShowClient.js
'use client';
import React, { useState } from 'react';
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Button } from '@/components/ui/button';
import { z } from 'zod';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

const categorySchema = z.object({
  category: z.string().nonempty('Category is required'),
  subCategory: z.string().nonempty('Subcategory is required'),
});

export default function ShowClient({ categories }) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [subCategories, setSubCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedSubCategory, setSelectedSubCategory] = useState(null);
  const [chainCategories, setChainCategories] = useState([]);

  const fetchSubCategories = async (subCategory) => {
    setLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8080/api/v2/category/${subCategory}/subcategories`,
      );

      if (!response.ok) {
        throw new Error('Failed to fetch subcategories');
      }
      const data = await response.json();
      setSubCategories(data.data.subCategories || null);
      setChainCategories(data.data?.chainCategories || []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCategoryChange = (value) => {
    setSelectedCategory(value);
    setSelectedSubCategory(null);
    const category = categories.find((cat) => cat.name === value);
    setSubCategories(category?.subCategories || []);
  };

  const handleSubCategoryChange = (value) => {
    setSelectedSubCategory(value);
    fetchSubCategories(value);
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    reset,
  } = useForm({
    resolver: zodResolver(categorySchema),
  });

  const onSubmit = (data) => {
    console.log('Form Data:', data);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="p-6 max-w-lg mx-auto bg-white rounded-md">
      <h1 className="font-semibold text-2xl mb-4">Category Tree</h1>

      <form onSubmit={handleSubmit(onSubmit)}>

        {/* Select Main Category */}
        {selectedSubCategory === null && (
          <div>
            <Select
              {...register('category')}
              onValueChange={(value) => {
                handleSubCategoryChange(value);
                setValue('category', value); // Set the form value for category
              }}
            >
              <SelectTrigger>
                <SelectValue placeholder="Select a category" />
              </SelectTrigger>
              <SelectContent>
                <SelectGroup>
                  {categories.map((category) => (
                    <SelectItem key={category.name} value={category.name}>
                      <span className="font-bold text-lg">{category.name}</span>
                    </SelectItem>
                  ))}
                </SelectGroup>
              </SelectContent>
            </Select>
            {errors.category && <p className="text-red-600">{errors.category.message}</p>}
          </div>
        )}

        {/* Chain Categories */}
        {chainCategories.length > 0 && (
          <div className="mt-6">
            <h2 className="font-bold text-lg">Selected categories chain:</h2>
            <p className="text-lg font-medium">
              <span
                className="text-blue-600 cursor-pointer"
                onClick={() => {
                  setSelectedSubCategory(null);
                  setSubCategories([]);
                  setChainCategories([]);
                  reset(); // Reset form values
                }}
              >
                ...
              </span>
              {' / '}
              {chainCategories.slice().reverse().map((chainCategory, index) => (
                <span
                  key={index}
                  className="text-blue-600 cursor-pointer"
                  onClick={() => handleSubCategoryChange(chainCategory)}
                >
                  {chainCategory}
                  {index < chainCategories.length - 1 && ' / '}
                </span>
              ))}
            </p>
          </div>
        )}

        {/* Subcategories Dropdown */}
        {subCategories.length > 0 && (
          <div className="mt-6">
            <h2 className="font-bold text-lg">
              {selectedSubCategory ? `Subcategories of ${selectedSubCategory}` : `Subcategories of ${selectedCategory}`}
            </h2>
            <Select
              {...register('subCategory')}
              onValueChange={(value) => {
                handleSubCategoryChange(value);
                setValue('subCategory', value); // Set the form value for subcategory
              }}
            >
              <SelectTrigger>
                <SelectValue placeholder="Select a sub-category" />
              </SelectTrigger>

              <SelectContent>
                <SelectGroup>
                  {subCategories.map((subCategory) => (
                    <SelectItem key={subCategory} value={subCategory}>
                      <span className="font-bold text-lg">{subCategory}</span>
                    </SelectItem>
                  ))}
                </SelectGroup>
              </SelectContent>
            </Select>
            {errors.subCategory && <p className="text-red-600">{errors.subCategory.message}</p>}
          </div>
        )}

        {subCategories.length === 0 && selectedSubCategory !== null && (
          <Button>next</Button>
        )}
      </form>
    </div>
  );
}