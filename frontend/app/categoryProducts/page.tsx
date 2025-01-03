import React from 'react';
import Sidebar from '@/components/CategorySidebar';
import {ScrollArea, ScrollBar} from '@/components/ui/scroll-area';
import {CardDemo} from '@/components/Card';
import {Separator} from '@/components/ui/separator';

interface Data {
    id: string;
    title: string;
    description: string;
    img: string;
    price: number;
    category: string;
    label: string;
}

interface Props {
    searchParams: { category?: string };
}

export default async function CategoryProductPage({searchParams}: Props) {
    const selectedCategory = searchParams.category || 'All';

    // Directly fetch products data
    const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
    const url =
        selectedCategory === 'All'
            ? `${baseUrl}/api/mock-data`
            : `${baseUrl}/api/mock-data?category=${encodeURIComponent(selectedCategory)}`;

    let products: Data[] = [];
    try {
        const response = await fetch(url, {cache: 'no-store'});

        if (!response.ok) {
            throw new Error('Failed to fetch products');
        }

        products = await response.json();
    } catch (error) {
        console.error('Error fetching products:', error);
    }

    return (
        <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden">
            <h1 className="text-2xl font-bold my-6">
                {selectedCategory === 'All' ? 'All Products' : `${selectedCategory} Products`}
            </h1>

            <div className="flex">
                {/* Sidebar */}
                <Sidebar selectedCategory={selectedCategory}/>

                {/* Main Content */}
                <div className="flex-1 ml-6">
                    <div className="container mx-auto px-4 w-full space-y-6">
                        {/* Best Selling Section */}
                        <div className="w-full mb-6">
                            <h2 className="text-xl font-semibold space-x-4 p-4">Best Selling</h2>
                            <ScrollArea className="w-full overflow-x-auto">
                                {products.filter((product) => product.label === 'Best Selling').length === 0 ? (
                                    <p className="text-center text-gray-500">No products found in this label.</p>
                                ) : (
                                    <div className="flex w-max space-x-4 p-4">
                                        {products
                                            .filter((product) => product.label === 'Best Selling')
                                            .map((item) => (
                                                <CardDemo key={item.id} {...item} />
                                            ))}
                                    </div>
                                )}
                                <ScrollBar orientation="horizontal"/>
                            </ScrollArea>
                        </div>

                        <Separator/>

                        {/* Limited Time Deals Section */}
                        <div className="w-full mb-6">
                            <h2 className="text-xl font-semibold space-x-4 p-4">Limited Time Deals</h2>
                            <ScrollArea className="w-full overflow-x-auto">
                                {products.filter((product) => product.label === 'Limited Time Deals').length === 0 ? (
                                    <p className="text-center text-gray-500">No products found in this label.</p>
                                ) : (
                                    <div className="flex w-max space-x-4 p-4">
                                        {products
                                            .filter((product) => product.label === 'Limited Time Deals')
                                            .map((item) => (
                                                <CardDemo key={item.id} {...item} />
                                            ))}
                                    </div>
                                )}
                                <ScrollBar orientation="horizontal"/>
                            </ScrollArea>
                        </div>

                        <Separator/>

                        {/* Top Rated Section */}
                        <div className="w-full mb-6">
                            <h2 className="text-xl font-semibold space-x-4 p-4">Top Rated</h2>
                            <ScrollArea className="w-full overflow-x-auto">
                                {products.filter((product) => product.label === 'Top Rated').length === 0 ? (
                                    <p className="text-center text-gray-500">No products found in this label.</p>
                                ) : (
                                    <div className="flex w-max space-x-4 p-4">
                                        {products
                                            .filter((product) => product.label === 'Top Rated')
                                            .map((item) => (
                                                <CardDemo key={item.id} {...item} />
                                            ))}
                                    </div>
                                )}
                                <ScrollBar orientation="horizontal"/>
                            </ScrollArea>
                        </div>

                        <Separator/>
                    </div>
                </div>
            </div>
        </div>
    );
}
