'use client';
import React, { use } from 'react';

export default function SellerCategoryButton({
                                               button,
                                             }: {
  button: Promise<{ id: string; title: string }[]>;
}) {
  const allPosts = use(button);  // Use hook to resolve the promise

  return (
    <ul>
      {allPosts.map((post) => (
        <li key={post.id}>{post.title}</li>
      ))}
    </ul>
  );
}
















// import React from "react";
//
// interface SellerCategoryButtonProps {
//   onClick: () => void;
// }
//
// const SellerCategoryButton: React.FC<SellerCategoryButtonProps> = ({ onClick }) => {
//   return (
//     <button
//       onClick={onClick}
//       className="flex items-center space-x-2 bg-gray-300 text-gray-700 font-medium px-4 py-2 rounded-lg hover:bg-gray-400 transition"
//     >
//       <svg
//         xmlns="http://www.w3.org/2000/svg"
//         className="h-5 w-5"
//         fill="none"
//         viewBox="0 0 24 24"
//         stroke="currentColor"
//       >
//         <path
//           strokeLinecap="round"
//           strokeLinejoin="round"
//           strokeWidth={2}
//           d="M4 6h16M4 12h16M4 18h16"
//         />
//       </svg>
//       <span>Categories</span>
//     </button>
//   );
// };
//
// export default SellerCategoryButton;
