import React from 'react';


type Props = {
  categories: string[]; // Array of category names as strings
  onCategorySelect: (categoryId: string) => void;
};

const SellerCategoryList: React.FC<Props> = ({ categories, onCategorySelect }) => {
  return (
    <div className=" p-4 ">
      {categories.map((category) => (
        <div
          key={category}
          className="cursor-pointer py-2 px-4 "
          onClick={() => onCategorySelect(category)} // use onCategorySelect here
        >
          {category}
        </div>
      ))}
    </div>
  );
};

export default SellerCategoryList;
