'use client';
import React, { useEffect, useState } from 'react';
import { Button } from '@/components/ui/button';

const CategorySelector = ({ onCategorySelect, onSubCategorySelect, nextStep }) => {
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
      setSubCategories(data.data.subCategories || null);
      setChainCategories(data.data?.chainCategories || []);
      // console.log(categories);
      // console.log(subCategories);
      // console.log(chainCategories);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Handle category selection
  const handleCategoryChange = (value) => {
    setSelectedCategory(value);
    setSelectedSubCategory(null);
    handleSubCategoryChange(value);
    const category = categories.find((cat) => cat.name === value);
    setSubCategories(category?.subCategories || []);
    onCategorySelect(value); // Notify parent form
  };

  // Handle subcategory selection
  const handleSubCategoryChange = (value) => {
    setSelectedSubCategory(value);
    fetchSubCategories(value);
    onSubCategorySelect(value); // Notify parent form
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="p-6 max-w-lg mx-auto bg-white rounded-md">
      <h1 className="font-bold text-center text-3xl">Select The Category List</h1>

      {/* Select Main Category */}
      {selectedSubCategory === null && (
        <div>
          <h2 className="font-bold text-lg mb-2">Select a category</h2>
          <div className="space-y-2">
            {categories.map((category) => (
              <div
                key={category.name}
                onClick={() => handleCategoryChange(category.name)}
                className={`p-2 rounded-md cursor-pointer ${
                  selectedCategory === category.name
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-200 hover:bg-gray-300'
                }`}
              >
                <span className="font-bold text-lg">{category.name}</span>
              </div>
            ))}
          </div>
        </div>
      )}


      {/* Chain Categories */}
      {chainCategories.length > 0 && (
        <div className="mt-6">
          <div className="flex items-center gap-2 flex-wrap">
            <span
              className="px-2 py-1 bg-blue-100 text-blue-600 font-semibold text-sm rounded-md cursor-pointer hover:bg-blue-200"
              title="Reset to start"
              onClick={() => {
                setSelectedSubCategory(null);
                setSubCategories([]);
                setChainCategories([]);
              }}
            >
        ...
      </span>

            {/* Category Chain */}
            {chainCategories.slice().reverse().map((chainCategory, index) => (
              <React.Fragment key={index}>
          <span
            className="px-2 py-1 bg-gray-100 text-gray-800 font-semibold text-sm rounded-md cursor-pointer hover:border-brand-dark"
            title={`Go to ${chainCategory}`}
            onClick={() => handleSubCategoryChange(chainCategory)}
          >
            {chainCategory}
          </span>
                {index < chainCategories.length - 1 && (
                  <span className="text-gray-400">/</span> // Separator
                )}
              </React.Fragment>
            ))}
          </div>
        </div>
      )}

      {/* Subcategories Dropdown */}
      {subCategories.length > 0 && (
        <div className="mt-6">
          <h2 className="font-bold text-lg pb-2 pt-2">
            {selectedSubCategory ? `SELECT THE OF ${selectedSubCategory}` : `SELECT THE OF ${selectedCategory}`}
          </h2>
          <div className="space-y-2">
            {subCategories.map((subCategory) => (
              <div
                key={subCategory}
                onClick={() => handleSubCategoryChange(subCategory)}
                className={`p-2 rounded-md cursor-pointer ${
                  selectedSubCategory === subCategory
                    ? 'bg-brand-dark text-white'
                    : 'bg-gray-200 hover:bg-gray-300'
                }`}
              >
                <span className="font-bold text-lg">{subCategory}</span>
              </div>
            ))}
          </div>
        </div>
      )}

      {subCategories.length === 0 && selectedSubCategory !== null && (
        <div>
          {/*<p className="text-xs">YOU HAVE SELECTED THE LEAF CATEGORY, CLICK NEXT TO CONTINUE</p>*/}
          <Button
            onClick={nextStep}
            type="button"
            className="w-full mt-8"
          >
            Next
          </Button>
        </div>
      )}
    </div>
  );
};

export default CategorySelector;