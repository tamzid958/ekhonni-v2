import React from "react";
import {ComboboxPopover} from "@/components/PopoverFilter";
import {CheckboxReactHookFormMultiple} from "@/components/CheckboxFilter";
import {CardDemo} from "@/components/Card";
import {SliderDemo} from "@/components/PriceRangeFilter";
import {Button} from "@/components/ui/button";

// Need to adjust the height of left filter bar
// Need to add pagination

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
        <div className="bg-brand-bright">
            <div className="flex justify-center items-center w-full text-2xl p-3 ">
                Search Results for your search...
            </div>
            <div className="flex flex-row ml-40 mr-40 mb-8">
                <div className="w-1/4 flex flex-col">
                    <div className="bg-white mb-4 border-[1px] border-gray-700 rounded-lg p-4 m-2">
                        SORT BY:
                        <div>
                            <Button variant="custom" className="p-2 m-2">Popular</Button>
                            <Button variant="custom" className="p-2 m-2">Lowest Price</Button>
                            <Button variant="custom" className="p-2 m-2">Highest Price</Button>
                            <Button variant="custom" className="p-2 m-2">Bestselling</Button>
                            <Button variant="custom" className="p-2 m-2">Recent Products</Button>
                        </div>
                    </div>
                    <div className="bg-white mb-4 border-[1px] border-gray-700 rounded-lg p-4 m-2">
                        <p className="text-bold mb-4">FILTER BY PRICE</p>
                        <div className="flex justify-center items-center">
                            <SliderDemo/>
                        </div>
                    </div>
                    <div className="bg-white border-[1px] border-gray-700 rounded-lg p-4 m-2">
                        <CheckboxReactHookFormMultiple/>
                    </div>
                </div>
                <div
                    className="bg-white w-3/4 flex flex-col border-[1px] border-gray-700 rounded-lg p-4 m-2">
                    <div className="flex justify-end">
                        <ComboboxPopover/>
                    </div>
                    <div className="space-y-6 container mx-auto my-4 px-4">
                        <h1 className="text-2xl font-bold my-6">All Products</h1>
                        <div>
                            <h2 className="text-xl font-semibold my-4">Best Selling</h2>
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                                {allProductsItems.map((item) => (
                                    <CardDemo key={item.title} {...item} />
                                ))}
                            </div>
                        </div>
                        <div>
                            <h2 className="text-xl font-semibold my-4">Limited Time Deals</h2>
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                                {allProductsItems.map((item) => (
                                    <CardDemo key={item.title} {...item} />
                                ))}
                            </div>
                        </div>
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
