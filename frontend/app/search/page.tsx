import React from "react";
import {ComboboxPopover} from "@/components/PopoverFilter";
import {CheckboxReactHookFormMultiple} from "@/components/CheckboxFilter";
import {CardDemo} from "@/components/Card";

// Need to adjust the height of left filter bar
// Need to add pagination
// Need to add double slider for price filtering

interface data {
    title: string;
    description: string;
    img: string;
    price: number;
    category: string;
}

export default async function Search() {
    const response = await fetch('http://localhost:3000/api/allProduct', {cache: 'no-store'});
    if (!response.ok) {
        throw new Error('Failed to fetch data');
    }
    const allProductsItems: data[] = await response.json();

    return (
        <div className="bg-white">
            <div className="flex justify-center items-center w-full text-2xl p-3 ">
                Search Results for your search...
            </div>
            <div className="flex flex-row ml-40 mr-40 mb-8">
                <div
                    className="w-1/4 flex flex-col border-2 border-gray-700 rounded-lg p-4 m-2">
                    <CheckboxReactHookFormMultiple/>
                </div>
                <div className="w-3/4 flex flex-col border-2 border-gray-700 rounded-lg p-4 m-2">
                    <div className="flex justify-end">
                        <ComboboxPopover/>
                    </div>

                    <div className="space-y-6 container mx-auto my-4 px-4">
                        <h1 className="text-2xl font-bold my-6">All Products</h1>

                        {/* Best Selling Section */}
                        <div>
                            <h2 className="text-xl font-semibold my-4">Best Selling</h2>
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                                {allProductsItems.map((item) => (
                                    <CardDemo key={item.title} {...item} />
                                ))}
                            </div>
                        </div>

                        {/* Limited Time Deals Section */}
                        <div>
                            <h2 className="text-xl font-semibold my-4">Limited Time Deals</h2>
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                                {allProductsItems.map((item) => (
                                    <CardDemo key={item.title} {...item} />
                                ))}
                            </div>
                        </div>

                        {/* Top Rated Section */}
                        <div>
                            <h2 className="text-xl font-semibold my-4">Top Rated</h2>
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                                {allProductsItems.map((item) => (
                                    <CardDemo key={item.title} {...item} />
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
