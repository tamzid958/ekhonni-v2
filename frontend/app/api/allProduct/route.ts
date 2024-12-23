export async function GET(req: Request) {

    const allProducts = [
        {
            title: "Smartphone XYZ",
            description: "A high-end smartphone with a 6.5-inch display, 128GB storage, and 5G connectivity.",
            price: 699.99,
            category: "Electronics",
            img: "https://images.unsplash.com/photo-1581091012185-1d75f72474e7"
        },
        {
            title: "4K Smart TV",
            description: "A 55-inch 4K Ultra HD Smart TV with streaming apps and voice control.",
            price: 499.99,
            category: "Electronics",
            img: "https://images.unsplash.com/photo-1501594907358-68e0c39656c8"
        },
        {
            title: "Wireless Earbuds",
            description: "True wireless earbuds with active noise cancellation and 20 hours of battery life.",
            price: 129.99,
            category: "Audio",
            img: "https://images.unsplash.com/photo-1593642532400-7b1c7d3b6e5e"
        },
        {
            title: "Bluetooth Speaker",
            description: "Portable Bluetooth speaker with 360-degree sound and 10 hours of battery life.",
            price: 79.99,
            category: "Audio",
            img: "https://images.unsplash.com/photo-1613078484518-b5b99d7207b7"
        },
        {
            title: "Fitness Tracker",
            description: "A waterproof fitness tracker with heart rate monitor, sleep tracking, and step counting.",
            price: 59.99,
            category: "Health & Fitness",
            img: "https://images.unsplash.com/photo-1593642532400-dde2b1e37e01"
        },
        {
            title: "Electric Kettle",
            description: "A fast-boiling electric kettle with a 1.5-liter capacity and auto shut-off feature.",
            price: 34.99,
            category: "Home Appliances",
            img: "https://images.unsplash.com/photo-1580748235127-0c99ed7f191b"
        },
        {
            title: "Gaming Laptop",
            description: "A powerful gaming laptop with a 15.6-inch screen, 16GB RAM, and NVIDIA graphics card.",
            price: 1299.99,
            category: "Electronics",
            img: "https://images.unsplash.com/photo-1532674023142-7f703be43ea7"
        },
        {
            title: "Wireless Headphones",
            description: "Noise-cancelling wireless headphones with 20 hours of battery life.",
            price: 149.99,
            category: "Audio",
            img: "https://images.unsplash.com/photo-1533028490397-b9054f9db4fa"
        },
        {
            title: "Leather Jacket",
            description: "A premium leather jacket for men, designed for comfort and style.",
            price: 199.99,
            category: "Fashion",
            img: "https://images.unsplash.com/photo-1560835039-6093966f393b"
        },
        {
            title: "Smartwatch",
            description: "A stylish smartwatch with fitness tracking, heart rate monitoring, and notifications.",
            price: 179.99,
            category: "Health & Fitness",
            img: "https://images.unsplash.com/photo-1602096604050-b6745526a11f"
        }
    ];


    return new Response(JSON.stringify(allProducts), {
        status: 200,
        headers: {
            "Content-Type": "application/json"
        }
    });
}