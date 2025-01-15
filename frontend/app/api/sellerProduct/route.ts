import { NextResponse } from "next/server";

export async function GET(request: Request) {
  const url = new URL(request.url);
  const categoryId = url.searchParams.get("categoryId");

  const products = [
    {
      id: "101",
      name: "Laptop",
      status: "running",
      categoryId: "1",
      image: "/images/laptop.jpg",
    },
    {
      id: "102",
      name: "Headphones",
      status: "sold",
      categoryId: "1",
      image: "/images/headphones.jpg",
    },
    {
      id: "201",
      name: "Novel",
      status: "running",
      categoryId: "2",
      image: "/images/novel.jpg",
    },
    {
      id: "301",
      name: "Shirt",
      status: "running",
      categoryId: "3",
      image: "/images/shirt.jpg",
    },
    {
      id: "302",
      name: "Jeans",
      status: "sold",
      categoryId: "3",
      image: "/images/jeans.jpg",
    },
    {
      id: "401",
      name: "Microwave",
      status: "running",
      categoryId: "4",
      image: "/kettle.jpg",
    },
  ];

  const filteredProducts = categoryId
    ? products.filter((product) => product.categoryId === categoryId)
    : products;

  return NextResponse.json(filteredProducts);
}
