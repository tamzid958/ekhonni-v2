import * as React from 'react';

interface data {
  id: number;
  title: string;
  subTitle: string;
  description: string;
  img: string;
  price: number;
}

export function CardForSearch({ id, title, subTitle, description, img, price }: data) {
  return (
    <div
      className="relative flex w-full max-w-xs flex-col overflow-hidden rounded-lg border border-gray-100 bg-white shadow-md">
      <img className="object-cover h-48"
           src={img}
           alt="product image" />
      <div className="mt-4 px-5">
        <h5 className="text-xl tracking-tight text-slate-900">{title}</h5>
        <h5 className="text-sl tracking-tight text-slate-900">{subTitle}</h5>
        <div className="mt-2 mb-5 flex items-center justify-between">
          <p>
            <span className="text-xl font-bold text-slate-900">{price}</span>
          </p>
        </div>
      </div>
    </div>
  );
}
