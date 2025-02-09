'use client'

import React, { useState } from "react";

interface Product {
  id: string;
  name: string;
  description: string;
  price: string;
  images: string[];
}

const Shop = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [formData, setFormData] = useState<Omit<Product, "id">>({
    name: "",
    description: "",
    price: "",
    images: [],
  });
  const [showForm, setShowForm] = useState(false);
  const [userRole] = useState<"seller" | "buyer">("seller"); // Simplified role context for this example

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleImageAdd = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;

    if (files) {
      Array.from(files).forEach((file) => {
        const reader = new FileReader();
        reader.onload = () => {
          setFormData((prev) => ({
            ...prev,
            images: [...prev.images, reader.result as string],
          }));
        };
        reader.readAsDataURL(file);
      });
    }

    e.target.value = "";
  };

  const handleImageRemove = (index: number) => {
    setFormData((prev) => ({
      ...prev,
      images: prev.images.filter((_, i) => i !== index),
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.name || !formData.price || formData.images.length === 0) {
      alert("Please fill in all required fields.");
      return;
    }

    const newProduct: Product = {
      id: crypto.randomUUID(),
      ...formData,
    };

    setProducts((prev) => [...prev, newProduct]);

    setFormData({
      name: "",
      description: "",
      price: "",
      images: [],
    });
    setShowForm(false);

    alert("Product posted successfully!");
  };

  const handleDeleteProduct = (id: string) => {
    setProducts((prev) => prev.filter((product) => product.id !== id));
    alert("Product removed successfully!");
  };

  return (
    <div className="bg-[#FAF7F0]">
      <div className="min-h-screen bg-gray-50 flex flex-col items-center p-6">
        {userRole === "seller" && !showForm && (
          <button
            onClick={() => setShowForm(true)}
            className="bg-sky-400 text-white py-2 px-4 rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Add Product
          </button>
        )}

        {showForm && userRole === "seller" && (
          <form
            onSubmit={handleSubmit}
            className="bg-white shadow-md rounded-lg p-6 max-w-md w-full mt-8"
          >
            {/* Form Fields */}
            <div className="mb-4">
              <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                Product Name <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm"
                placeholder="Enter product name"
                required
              />
            </div>

            <div className="mb-4">
              <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                Description
              </label>
              <textarea
                id="description"
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm"
                placeholder="Enter product description"
              />
            </div>

            <div className="mb-4">
              <label htmlFor="price" className="block text-sm font-medium text-gray-700">
                Price <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                id="price"
                name="price"
                value={formData.price}
                onChange={handleInputChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm"
                placeholder="Enter price (e.g., 1000৳)"
                required
              />
            </div>

            <div className="mb-4">
              <label htmlFor="images" className="block text-sm font-medium text-gray-700">
                Upload Images <span className="text-red-500">*</span>
              </label>
              <input
                type="file"
                id="images"
                multiple
                accept="image/*"
                onChange={handleImageAdd}
                className="mt-1 block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:bg-sky-400 file:text-white hover:file:bg-sky-600"
              />
              {formData.images.map((image, index) => (
                <div key={index} className="flex items-center mt-2">
                  <img
                    src={image}
                    alt={`Uploaded ${index + 1}`}
                    className="w-20 h-20 object-cover border rounded-md"
                  />
                  <button
                    type="button"
                    onClick={() => handleImageRemove(index)}
                    className="ml-2 bg-red-500 text-white px-2 py-1 rounded-md shadow-md hover:bg-red-600"
                  >
                    Remove
                  </button>
                </div>
              ))}
            </div>

            <div className="flex items-center justify-between">
              <button
                type="submit"
                className="bg-blue-500 text-white py-2 px-4 rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
              >
                Post Product
              </button>
              <button
                type="button"
                onClick={() => setShowForm(false)}
                className="bg-gray-500 text-white py-2 px-4 rounded-md shadow-md hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
              >
                Cancel
              </button>
            </div>
          </form>
        )}

        {/* Product List */}
        <div className="flex-1 container mx-auto mt-4 px-24 lg:px-32">
          <h2 className="text-xl font-bold text-gray-800 mb-4">
            {userRole === "seller" ? "Your Products" : "Available Products"}
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {products.map((product) => (
              <div
                key={product.id}
                className="border rounded-lg shadow-md bg-white p-4 flex flex-col items-center"
              >
                <div className="flex space-x-2 mb-4">
                  {product.images.map((image, index) => (
                    <img
                      key={index}
                      src={image}
                      alt={`${product.name} - ${index + 1}`}
                      className="w-40 h-40 object-cover"
                    />
                  ))}
                </div>
                <h3 className="text-lg font-semibold text-gray-700">{product.name}</h3>
                <p className="text-gray-500">{product.description}</p>
                <p className="text-gray-700 font-bold">{product.price}</p>
                {userRole === "seller" && (
                  <button
                    onClick={() => handleDeleteProduct(product.id)}
                    className="mt-2 bg-red-500 text-white py-1 px-4 rounded-md shadow-md hover:bg-red-600"
                  >
                    Delete
                  </button>
                )}
                {userRole === "buyer" && (
                  <button className="mt-2 bg-green-500 text-white py-1 px-4 rounded-md shadow-md hover:bg-green-600">
                    Place Bid
                  </button>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Shop;
