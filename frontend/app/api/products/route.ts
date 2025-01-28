import { NextResponse } from "next/server";

export async function GET() {
  return NextResponse.json([
    {
      id: 1,
      title: "Smartphone XYZ",
      description: "A high-end smartphone with a 6.5-inch display, 128GB storage, and 5G connectivity.",
      price: 699.99,
      category: "Electronics",
      img: "/smartphone.jpg"
    },
    {
      id: 2,
      title: "4K Smart TV",
      description: "A 55-inch 4K Ultra HD Smart TV with streaming apps and voice control.",
      price: 499.99,
      category: "Electronics",
      img: "/smartTv.webp"
    },
    {
      id: 3,
      title: "Wireless Earbuds",
      description: "True wireless earbuds with active noise cancellation and 20 hours of battery life.",
      price: 129.99,
      category: "Audio",
      img: "/earbuds.jpg"
    },
    {
      id: 4,
      title: "Bluetooth Speaker",
      description: "Portable Bluetooth speaker with 360-degree sound and 10 hours of battery life.",
      price: 79.99,
      category: "Audio",
      img: "/speaker.webp"
    },
    {
      id: 5,
      title: "Fitness Tracker",
      description: "A waterproof fitness tracker with heart rate monitor, sleep tracking, and step counting.",
      price: 59.99,
      category: "Health & Fitness",
      img: "/tracker.webp"
    },
    {
      id: 6,
      title: "Electric Kettle",
      description: "A fast-boiling electric kettle with a 1.5-liter capacity and auto shut-off feature.",
      price: 34.99,
      category: "Home Appliances",
      img: "/kettle.jpg"
    }
  ]);
}