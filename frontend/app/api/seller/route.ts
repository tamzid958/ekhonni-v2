import { NextResponse } from "next/server";

export async function GET() {
  return NextResponse.json([
    {
      sellerId: "S12345",
      logo: "https://example.com/logo1.png",
      pageName: "Tech Haven",
      categories: [
        {
          categoryName: "Electronics",
          products: [
            {
              productId: "P1001",
              productName: "Smartphone",
              price: 500,
              image: "https://example.com/smartphone.jpg",
            },
            {
              productId: "P1002",
              productName: "Laptop",
              price: 1000,
              image: "https://example.com/laptop.jpg",
            },
          ],
        },
        {
          categoryName: "Accessories",
          products: [
            {
              productId: "P2001",
              productName: "Wireless Earbuds",
              price: 50,
              image: "https://example.com/earbuds.jpg",
            },
            {
              productId: "P2002",
              productName: "Phone Case",
              price: 10,
              image: "https://example.com/phone-case.jpg",
            },
          ],
        },
      ],
    },
    {
      sellerId: "S67890",
      logo: "https://example.com/logo2.png",
      pageName: "Fashion Hub",
      categories: [
        {
          categoryName: "Clothing",
          products: [
            {
              productId: "P3001",
              productName: "T-Shirt",
              price: 20,
              image: "https://example.com/tshirt.jpg",
            },
            {
              productId: "P3002",
              productName: "Jeans",
              price: 40,
              image: "https://example.com/jeans.jpg",
            },
          ],
        },
        {
          categoryName: "Footwear",
          products: [
            {
              productId: "P4001",
              productName: "Sneakers",
              price: 60,
              image: "https://example.com/sneakers.jpg",
            },
            {
              productId: "P4002",
              productName: "Sandals",
              price: 30,
              image: "https://example.com/sandals.jpg",
            },
          ],
        },
      ],
    },
  ]);
}
