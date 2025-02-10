// Need to add pagination
'use client';
import { useFilterProducts } from '@/hooks/useFilterProducts';
import { useSearchParams } from 'next/navigation';
import { CheckboxReactHookFormMultiple } from '@/components/CheckboxFilter';
import { SliderDemo } from '@/components/PriceRangeFilter';
import { Button } from '@/components/ui/button';
import { CardForSearch } from './components/CardForSearch';
import Link from 'next/link';
import { useState } from 'react';
import { CheckboxReactHookFormMultipleCondition } from '@/components/CheckBoxFilterCondition';

interface Product {
  id: number;
  title: string;
  subTitle: string;
  description: string;
  price: number;
  division: string;
  address: string;
  condition: string;
  conditionDetails: string;
  status: string;
  createdAt: string;
  updatedAt: string;
  seller: {
    id: string;
    name: string;
  };
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
}

export default function Search() {
  const searchParams = useSearchParams();
  const query = searchParams.get('q') || 'No query provided';
  const [sortBy, setSortBy] = useState('');
  const [selecetedDivisions, setSelectedDivisions] = useState<string[]>([]);
  const [selectedConditions, setSelectedConditions] = useState<string[]>([]);
  const [value, setValue] = useState<[number, number]>([0, 100000]);

  const {
    products,
    error,
    isLoading,
  } = useFilterProducts(query, sortBy, selecetedDivisions, selectedConditions, value);

  if (error) return <div>failed to load</div>;
  if (isLoading) return <div>loading...</div>;

  const allProductsItems = products || [];

  const handleFilter = (newValue: [number, number]) => {
    //console.log('Filtered values:', newValue);
    setValue(newValue);
  };

  return (
    <div className="bg-brand-bright">
      <div className="flex justify-center items-center w-full text-2xl p-3 ">
        Search Results for your search: {query}
      </div>
      <div className="flex flex-row ml-40 mr-40 mb-8">
        <div className="w-1/4 flex flex-col">
          <div className="bg-white mb-4 border-[1px]  rounded-lg p-4 m-2">
            SORT BY:
            <div>
              <Button variant="custom" className="p-2 m-2" onClick={() => setSortBy('priceLowToHigh')}>
                Lowest Price
              </Button>
              <Button variant="custom" className="p-2 m-2" onClick={() => setSortBy('priceHighToLow')}>
                Highest Price
              </Button>
              <Button variant="custom" className="p-2 m-2" onClick={() => setSortBy('newlyListed')}>
                By Time
              </Button>
            </div>
          </div>
          <div className="bg-white mb-4 border-[1px] rounded-lg p-4 m-2">
            <p className="text-bold mb-4">FILTER BY PRICE</p>
            <div className="flex justify-center items-center">
              <SliderDemo value={value} setValue={setValue} onFilter={handleFilter} />
            </div>
          </div>
          <div className="bg-white border-[1px] rounded-lg p-4 m-2">
            <CheckboxReactHookFormMultiple setSelectedDivisions={setSelectedDivisions}
                                           selectedDivisions={selecetedDivisions} />
          </div>
          <div className="bg-white border-[1px] rounded-lg p-4 m-2">
            <CheckboxReactHookFormMultipleCondition setSelectedConditions={setSelectedConditions}
                                                    selectedConditions={selectedConditions} />
          </div>
        </div>
        <div
          className="bg-white w-3/4 flex flex-col border-[1px]  rounded-lg p-4 m-2">
          <div className="space-y-6 container mx-auto my-4 px-4">
            <h1 className="text-2xl font-bold my-6">All Products</h1>
            <hr />
            <div>
              <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                {allProductsItems.map((item) => (
                  <Link key={item.id} href={`/productDetails?id=${item.id}`} className="cursor-pointer">
                    <CardForSearch
                      id={item.id}
                      title={item.title}
                      subTitle={item.subTitle}
                      description={item.description}
                      img={item.images[0].imagePath}
                      price={item.price}
                    />
                  </Link>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}