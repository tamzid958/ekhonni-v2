import { NextResponse } from "next/server";

export async function GET() {
  const categories = [
    { id: "1", name: "Electronics" },
    { id: "2", name: "Books" },
    { id: "3", name: "Clothing" },
    { id: "4", name: "Home Appliances" },
  ];
  return NextResponse.json(categories);
}
