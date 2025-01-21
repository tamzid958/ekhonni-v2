'use client';
import {CakeSlice, Star} from 'lucide-react';
import {Button} from "@/components/ui/button";
import {QuickBid} from "@/components/QuickBid";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"
import { useState } from 'react';


//product condition stars -> image or icons?
//border and bg in the product description??
 export default function productDetails() {
  const [selectedIndex, setSelectedIndex] = useState(0);

  return (
    <div className="flex flex-col bg-brand-bright">
      <div className="flex flex-row ml-40 mr-40 pb-12 pt-12">
        <div className="flex flex-row">
          <div className="flex flex-col">
            {Array.from({length: 5}).map((_, index) => (
              <div key={index} className="pt-4">
                <img
                  src={`ad${index + 1}.png`}
                  alt={`public/AdsImage/ad ${index + 1}`}
                  className={`h-24 w-28 object-cover cursor-pointer ${
                    selectedIndex === index
                      ? "border-4 border-brand-dark"
                      : "border border-gray-200"
                  }`}
                  onClick={() => setSelectedIndex(index)}
                />
              </div>
            ))}
          </div>
          <div className="ml-4 mb-4 pt-4 pl-2 pr-2">
            <img
              src={`ad${selectedIndex + 1}.png`}
              alt={`public/AdsImage/ad ${selectedIndex + 1}`}
              className="h-[36rem] w-[56rem] object-cover rounded-xl"
            />
          </div>
        </div>
        <div className="w-4/12 mx-auto">
          <div className="pt-16 pl-12">
            <p className="text-4xl">Hermès</p>
            <p className="pt-2 italic">Vert dau Matte Alligator Birkin 20 Sellier Gold Hardware, 2023</p>
            <span className="inline-flex pt-4">
                            <CakeSlice className=""/>
                            <p className="text-gray-500 pl-2">Complimentary shipping</p>
                        </span>
            <hr className="border-gray-400 border-1 mt-1"/>
            <div className="inline-flex w-full pt-2 pb-4">
              <p className="text-xl font-bold">BASE PRICE</p>
              <p className="text-xl ml-auto text-right font-bold">$200</p>
            </div>
            <Popover>
              <PopoverTrigger asChild>
                <Button variant="custom" className="w-full font-bold">
                  START BIDDING
                </Button>
              </PopoverTrigger>
              <PopoverContent>Popover Content</PopoverContent>
            </Popover>


            <div className="pt-2">
              <Button variant="custom" className="w-full font-bold">ADD TO CART</Button>
            </div>
            <div className="pt-4 inline-flex">
              <div className="font-bold w-40">SELLER ID:</div>
              <div className="pl-2 italic">NJFNDLKF</div>
            </div>
            <br/>
            <div className="pt-2 font-bold inline-flex">
              <div className="w-40">
                SELLER RATING:
              </div>
              <div className="pl-2 inline-flex">
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
              </div>
            </div>
            <br/>
            <div className="pt-2 inline-flex">
              <div className="font-bold w-40">DELIEVERY FROM:</div>
              <div className="pl-2 italic">DHAKA</div>
            </div>
            <br/>
            <div className="pt-2 inline-flex">
              <div className="font-bold w-40">POST CREATED AT:</div>
              <div className="pl-2 italic">7 JANUARY 2025</div>
            </div>
          </div>
        </div>
      </div>

      <div className="flex flex-col pt-8 pl-40 pr-40">
        <div className="flex justify-center text-4xl pb-4">PRODUCT DESCRIPTION</div>
        <div className="flex flex-row border-gray-400 border-[1px] rounded-2xl p-6 w-full ">
          <div className="flex flex-col w-3/4 pl-4">
            <div className="text-xl font-bold">
              Description
            </div>
            <div className="pt-4">
              Hermès Vert dau Matte Alligator Birkin 20 Sellier Gold Hardware, 2023
            </div>
            <ul className="list-disc list-inside space-y-1 pt-1">
              <li>The interior is lined in a tonal Chèvre leather</li>
              <li>Includes lock, two keys, clochette, clochette dust bag, felt protector, dust bag, and
                box
              </li>
              <li>This item is final sale and not eligible for return</li>
            </ul>
            <div className="text-xl font-bold pt-4">
              <div className="pb-4">
                Condition Report
              </div>
              <div className="pl-0 pb-2 inline-flex">
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
                <Star className="w-[1.25rem] h-[1.25rem]"/>
              </div>
            </div>
            <div className="p-0">
              No visible signs of wear. Plastic on hardware.
            </div>
          </div>

          <div className="flex flex-col w-1/4">
            <div className="text-xl font-bold pt-4">
              YEAR
            </div>
            <div className="pt-4">
              2024
            </div>

            <div className="text-xl font-bold pt-4 pb-4">
              Dimensions
            </div>
            <div className="">Height: 6.1 inches / 15.5 cm</div>
            <div className="">Width: 7.87 inches / 20 cm</div>
            <div className="">Depth: 4.33 inches / 11 cm</div>
          </div>
        </div>
      </div>
      <QuickBid title={"SIMILAR PRODUCTS"}/>
    </div>
  );
}