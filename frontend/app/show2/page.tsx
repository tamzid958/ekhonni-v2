'use client';
import React, { useEffect, useState } from 'react';
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Button } from '@/components/ui/button';

export default function Show() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [subCategories, setSubCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedSubCategory, setSelectedSubCategory] = useState(null);
  const [chainCategories, setChainCategories] = useState([]);

  // Fetch initial categories
  useEffect(() => {
    const fetchCategories = async () => {
      setLoading(true);
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

  useEffect(() => {
    console.log('Updated Subcategories:', subCategories);
  }, [subCategories]);

  // Fetch subcategories dynamically
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
      //console.log(data.data.subCategories);
      setSubCategories(data.data.subCategories || null); // Update subcategories
      setChainCategories(data.data?.chainCategories || []);
      console.log('blikdbjs' + chainCategories);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Handle category selection
  const handleCategoryChange = (value) => {
    setSelectedCategory(value); // Update selected category
    setSelectedSubCategory(null); // Reset selected subcategory
    const category = categories.find((cat) => cat.name === value);
    setSubCategories(category?.subCategories || []); // Set initial subcategories
  };

  // Handle subcategory selection
  const handleSubCategoryChange = (value) => {
    setSelectedSubCategory(value); // Update selected subcategory
    fetchSubCategories(value); // Fetch next-level subcategories
    //console.log(subCategories);
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

      {/* Select Main Category */}
      {selectedSubCategory === null && (
        <div>
          <Select onValueChange={handleCategoryChange}>
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
        </div>
      )

      }

      {chainCategories.length > 0 && (
        <div className="mt-6">
          <h2 className="font-bold text-lg">Selected categories chain:</h2>
          <p className="text-lg font-medium">
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
          <Select onValueChange={handleSubCategoryChange}>
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
        </div>
      )}


      {subCategories.length === 0 && selectedCategory !== null && (
        <Button>next</Button>
      )}
    </div>
  );
}
