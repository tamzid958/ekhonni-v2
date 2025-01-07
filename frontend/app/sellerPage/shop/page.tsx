const SellerShopPage = () => {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
      {/* Replace the below mock cards with dynamic content */}
      {[1, 2, 3, 4, 5, 6].map((item) => (
        <div
          key={item}
          className="p-4 border rounded-lg shadow-sm hover:shadow-lg transition"
        >
          <img
            src="/path/to/product-image.jpg"
            alt="Product"
            className="w-full h-40 object-cover rounded"
          />
          <h3 className="mt-2 text-lg font-bold">Product {item}</h3>
          <p className="text-gray-600">$100.00</p>
        </div>
      ))}
    </div>
  );
};

export default SellerShopPage;
