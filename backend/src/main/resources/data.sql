-- Insert Roles (only ADMIN and USER now)
INSERT INTO role (id, name) VALUES
(101, 'ADMIN'),
(102, 'USER');

-- Insert Users (updated to remove account_id)
INSERT INTO users (id, name, email, password, phone, address, role_id, is_blocked, verified, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440000'::uuid, 'John Doe', 'john@example.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567890', '123 Main St', 102, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440001'::uuid, 'Jane Smith', 'jane@example.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567891', '456 Oak Ave', 102, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440002'::uuid, 'Admin User', 'admin@example.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567892', '789 Admin St', 101, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440003'::uuid, 'Mark Wilson', 'mark@example.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567893', '321 Oak Ave', 102, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440004'::uuid, 'Sarah Brown', 'sarah@example.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567894', '654 Pine St', 102, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440005'::uuid, 'Asif Iqbal', 'asif783810@gmail.com',
   '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567894', 'Dhamrai, Dhaka', 102, false, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440006'::uuid, 'Rifat Shariar Sakil', 'shariarsakil101@gmail.com',
   '$2a$12$Sdg6oZNAi0XulmEeJluSfu6HGG43mIoH0kCXlli6CJinjnSToY3km', '+1234567894', '654 Pine St', 102, false, true, NOW(), NOW());



-- Insert Accounts (updated total_earnings to 0.00)
INSERT INTO account (id, total_earnings, total_withdrawals, status, user_id, created_at, updated_at) VALUES
(101, 0.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW()),
(102, 0.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440001'::uuid, NOW(), NOW()),
(103, 0.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440002'::uuid, NOW(), NOW()),
(104, 0.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
(105, 0.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440004'::uuid, NOW(), NOW()),
(106, 100000.00, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
(107, 500, 0.00, 'ACTIVE', '550e8400-e29b-41d4-a716-446655440006'::uuid, NOW(), NOW());

INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10000, 'Travel & Nature', true, null, NOW(), NOW()),
(10001, 'Antiques', true, null, NOW(), NOW()),
(10002, 'Health & Beauty', true, null, NOW(), NOW()),
(10003, 'Sports & Outdoors', true, null, NOW(), NOW()),
(10004, 'Toys & Games', true, null, NOW(), NOW()),
(10005, 'Automotive', true, null, NOW(), NOW()),
(10006, 'Books & Stationery', true, null, NOW(), NOW()),
(10007, 'Groceries', true, null, NOW(), NOW()),
(10008, 'Office Supplies', true, null, NOW(), NOW());

-- Insert into Travel and Nature
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10009, 'Adventure Travel', true, 10000, NOW(), NOW()),
(10010, 'Camping & Hiking', true, 10000, NOW(), NOW()),
(10011, 'Wildlife & Safari', true, 10000, NOW(), NOW()),
(10012, 'Eco-Tourism', true, 10000, NOW(), NOW()),
(10013, 'Beach Holidays', true, 10000, NOW(), NOW()),
(10014, 'Cultural Tours', true, 10000, NOW(), NOW());

-- Insert into Adventure Travel
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10015, 'Mountain Climbing', true, 10009, NOW(), NOW()),
(10016, 'Rock Climbing', true, 10009, NOW(), NOW()),
(10017, 'Trekking', true, 10009, NOW(), NOW()),
(10018, 'Canoeing & Kayaking', true, 10009, NOW(), NOW()),
(10019, 'Desert Expeditions', true, 10009, NOW(), NOW()),
(10020, 'Polar Expeditions', true, 10009, NOW(), NOW());

-- Insert subcategories into Antiques
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10021, 'Furniture', true, 10001, NOW(), NOW()),
(10022, 'Jewelry', true, 10001, NOW(), NOW()),
(10023, 'Coins & Currency', true, 10001, NOW(), NOW()),
(10024, 'Instruments & Tools', true, 10001, NOW(), NOW()),
(10025, 'Watches & Clocks', true, 10001, NOW(), NOW()),
(10026, 'Clothing', true, 10001, NOW(), NOW());

-- Insert into Health & Beauty
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10027, 'Skincare', true, 10002, NOW(), NOW()),
(10028, 'Hair Care', true, 10002, NOW(), NOW()),
(10029, 'Makeup', true, 10002, NOW(), NOW()),
(10030, 'Fragrances', true, 10002, NOW(), NOW()),
(10031, 'Personal Hygiene', true, 10002, NOW(), NOW()),
(10032, 'Health Supplements', true, 10002, NOW(), NOW());

-- Insert into Sports & Outdoors
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10033, 'Fitness Equipment', true, 10003, NOW(), NOW()),
(10034, 'Water Sports', true, 10003, NOW(), NOW()),
(10035, 'Team Sports', true, 10003, NOW(), NOW()),
(10036, 'Winter Sports', true, 10003, NOW(), NOW()),
(10037, 'Fishing', true, 10003, NOW(), NOW()),
(10038, 'Cycling', true, 10003, NOW(), NOW());

-- Insert into Toys & Games
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10039, 'Board Games', true, 10004, NOW(), NOW()),
(10040, 'Action Figures', true, 10004, NOW(), NOW()),
(10041, 'Dolls & Plush Toys', true, 10004, NOW(), NOW()),
(10042, 'Building Toys', true, 10004, NOW(), NOW()),
(10043, 'Educational Toys', true, 10004, NOW(), NOW()),
(10044, 'Outdoor Toys', true, 10004, NOW(), NOW());

-- Insert into Automotive
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10045, 'Car Accessories', true, 10005, NOW(), NOW()),
(10046, 'Motorcycle Accessories', true, 10005, NOW(), NOW()),
(10047, 'Car Care', true, 10005, NOW(), NOW()),
(10048, 'Auto Electronics', true, 10005, NOW(), NOW()),
(10049, 'Replacement Parts', true, 10005, NOW(), NOW()),
(10050, 'Tires & Wheels', true, 10005, NOW(), NOW());

-- Insert into Books & Stationery
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10051, 'Fiction', true, 10006, NOW(), NOW()),
(10052, 'Non-Fiction', true, 10006, NOW(), NOW()),
(10053, 'Academic Books', true, 10006, NOW(), NOW()),
(10054, 'Notebooks & Diaries', true, 10006, NOW(), NOW()),
(10055, 'Art Supplies', true, 10006, NOW(), NOW()),
(10056, 'Office Stationery', true, 10006, NOW(), NOW());

-- Insert into Groceries
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10057, 'Fruits & Vegetables', true, 10007, NOW(), NOW()),
(10058, 'Meat & Seafood', true, 10007, NOW(), NOW()),
(10059, 'Dairy Products', true, 10007, NOW(), NOW()),
(10060, 'Beverages', true, 10007, NOW(), NOW()),
(10061, 'Snacks', true, 10007, NOW(), NOW()),
(10062, 'Pantry Staples', true, 10007, NOW(), NOW());

-- Insert into Office Supplies
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10063, 'Desk Accessories', true, 10008, NOW(), NOW()),
(10064, 'Office Furniture', true, 10008, NOW(), NOW()),
(10065, 'Writing Instruments', true, 10008, NOW(), NOW()),
(10066, 'Paper Products', true, 10008, NOW(), NOW()),
(10067, 'Printing Supplies', true, 10008, NOW(), NOW()),
(10068, 'Organizers', true, 10008, NOW(), NOW());


INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(10069, 'Furniture 2', true, 10021, NOW(), NOW()),
(10070, 'Furniture 3', true, 10069, NOW(), NOW()),
(10071, 'Furniture 4', true, 10070, NOW(), NOW());

-- Insert Products with leaf categories and updated IDs
INSERT INTO product (id, title, sub_title, description, price, division, address, status, condition, condition_details, category_id, seller_id, buyer_id, created_at, updated_at) VALUES
(101, 'iPhone 13 Pro', 'Slightly used iPhone 13 Pro', '256GB storage, excellent condition', 899.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Minimal signs of use', 10048, '550e8400-e29b-41d4-a716-446655440000'::uuid, NULL, NOW(), NOW()),
(102, 'MacBook Pro 2022', 'New MacBook Pro M1', 'Latest model with M1 chip', 1499.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Brand new', 10048, '550e8400-e29b-41d4-a716-446655440001'::uuid, NULL, NOW(), NOW()),
(103, 'Samsung Galaxy S21', 'Brand new Samsung Galaxy S21', 'Latest model, never used', 699.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Factory sealed', 10048, '550e8400-e29b-41d4-a716-446655440003'::uuid, NULL, NOW(), NOW()),
(104, 'Dell XPS 13', 'Slightly used Dell XPS 13', 'Compact and powerful laptop', 1299.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Very_Good', 'Minor wear on keyboard', 10048, '550e8400-e29b-41d4-a716-446655440004'::uuid, NULL, NOW(), NOW()),
(105, 'Leather Jacket', 'Genuine leather jacket', 'Stylish and durable', 199.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Never worn', 10026, '550e8400-e29b-41d4-a716-446655440000'::uuid, NULL, NOW(), NOW()),
(106, 'Leather Jacket', 'Genuine leather jacket', 'Stylish and durable', 199.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Never worn', 10026, '550e8400-e29b-41d4-a716-446655440005'::uuid, NULL, NOW(), NOW()),
(107, 'Leather Jacket', 'Genuine leather jacket', 'Stylish and durable', 199.99, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Never worn', 10026, '550e8400-e29b-41d4-a716-446655440005'::uuid, NULL, NOW(), NOW());

--(108, 'Sony WH-1000XM4 Headphones', 'Noise-canceling headphones', 'Top-notch sound quality', 350.00, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Unused', 10048, '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
--(109, 'Nike Air Max', 'Brand new running shoes', 'Comfortable and stylish', 120.00, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'In original box', 10033, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
--(110, 'Apple Watch Series 6', 'Used but in great condition', 'Advanced health features', 399.00, 'DHAKA', 'Dhaka', 'APPROVED', 'Very_Good', 'Minor scratches on screen', 10048, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
--(111, 'Canon EOS Rebel T7', 'Digital SLR Camera', 'Perfect for photography enthusiasts', 450.00, 'DHAKA', 'Dhaka', 'APPROVED', 'Like_New', 'Unused', 10048, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),

(202, 'Shawl', 'Beautiful Antique Shawl', 'A hand-crafted antique shawl', 3000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Minor wear and tear', 10026, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(303, 'Tea Pot', 'Antique Tea Pot', 'A rare antique teapot made of porcelain', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Slight chipping on the lid', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(304, 'Watch', 'Classic Antique Watch', 'A vintage watch with a leather strap', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Scratches on the glass', 10025, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(305, 'Marble', 'Antique Marble Collection', 'A set of antique marbles from the 19th century', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Some discoloration', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(306, 'Keyboard', 'Rare Antique Keyboard', 'A rare typewriter-style keyboard', 56000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Fully functional', 10001, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(352, 'Oil', 'Vintage Hair Oil', 'A rare bottle of vintage hair oil', 56000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Original packaging intact', 10028, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
(353, 'Oil', 'Vintage Hair Oil', 'A rare bottle of vintage hair oil', 56000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Original packaging intact', 10070, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW());

-- Add product images for the original products
INSERT INTO product_image (id, product_id, image_path, created_at, updated_at) VALUES
(100, 202, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/uqoduxx5ewvnchgsuols.jpg', NOW(), NOW()),
(101, 202, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031664/mfmihqj34q8p0zuuiekg.jpg', NOW(), NOW()),
(102, 303, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269109/hqrufykrmzwv9wq5xwyv.jpg', NOW(), NOW()),
(103, 303, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269112/x7htnhtwlw7uhvzuvhmi.jpg', NOW(), NOW()),
(104, 304, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269197/vldssebhqijs41pjanio.jpg', NOW(), NOW()),
(105, 304, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269198/wvld1ck8cwdmldbfhage.jpg', NOW(), NOW()),
(106, 305, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269363/dr2slp65tsk9z2n9rcs9.jpg', NOW(), NOW()),
(107, 305, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269364/sy87ffhehxuffkrczp8w.jpg', NOW(), NOW()),
(108, 306, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269460/plwi5lonfnwb4mvarfpy.jpg', NOW(), NOW()),
(109, 306, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269462/xpwozjsexnldymbyal1c.jpg', NOW(), NOW()),
(110, 352, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284715/pjgaqxks4wrh34civzle.jpg', NOW(), NOW()),
(111, 352, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284716/gnoxqfkpgcgvly3yjbfa.jpg', NOW(), NOW()),
(112, 353, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284715/pjgaqxks4wrh34civzle.jpg', NOW(), NOW()),
(113, 353, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284716/gnoxqfkpgcgvly3yjbfa.jpg', NOW(), NOW());


INSERT INTO product_image (id, product_id, image_path, created_at, updated_at) VALUES
(13, 101, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/iphone13pro.jpg', NOW(), NOW()),
(14, 102, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/macbookpro.jpg', NOW(), NOW()),
(15, 103, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/galaxys21.jpg', NOW(), NOW()),
(16, 104, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/dellxps13.jpg', NOW(), NOW()),
(17, 105, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/leatherjacket1.jpg', NOW(), NOW()),
(18, 106, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/leatherjacket2.jpg', NOW(), NOW()),
(19, 107, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/leatherjacket3.jpg', NOW(), NOW());
--(20, 108, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/sonyheadphones.jpg', NOW(), NOW()),
--(21, 109, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/nikeairmax.jpg', NOW(), NOW()),
--(22, 110, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/applewatch.jpg', NOW(), NOW()),
--(23, 111, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/canoneos.jpg', NOW(), NOW());


-- Bids where Asif is the Bidder (Buyer)
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1001, 101, '550e8400-e29b-41d4-a716-446655440005'::uuid, 100.00, 'BDT', 'ACCEPTED', NOW(), NOW()),  -- Accepted
(1002, 101, '550e8400-e29b-41d4-a716-446655440003'::uuid, 875.00, 'BDT', 'PENDING', NOW(), NOW()),   -- Pending
(1006, 102, '550e8400-e29b-41d4-a716-446655440005'::uuid, 150.00, 'BDT', 'PENDING', NOW(), NOW()),  -- Accepted
(1003, 102, '550e8400-e29b-41d4-a716-446655440004'::uuid, 1450.00, 'BDT', 'PENDING', NOW(), NOW()),  -- Pending
(1008, 104, '550e8400-e29b-41d4-a716-446655440005'::uuid, 250.00, 'BDT', 'PENDING', NOW(), NOW()),  -- Accepted
(1009, 105, '550e8400-e29b-41d4-a716-446655440005'::uuid, 300.00, 'BDT', 'PENDING', NOW(), NOW()); -- Accepted
--(1016, 108, '550e8400-e29b-41d4-a716-446655440005'::uuid, 340.00, 'BDT', 'PENDING', NOW(), NOW());  -- Accepted

---- Bids where Asif is the Seller
--INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
--(1010, 106, '550e8400-e29b-41d4-a716-446655440000'::uuid, 1000.00, 'BDT', 'PENDING', NOW(), NOW()), -- Accepted
--(1011, 106, '550e8400-e29b-41d4-a716-446655440001'::uuid, 800.00, 'BDT', 'PENDING', NOW(), NOW()),
--(1013, 107, '550e8400-e29b-41d4-a716-446655440000'::uuid, 1000.00, 'BDT', 'PENDING', NOW(), NOW()), -- Accepted
--(1014, 107, '550e8400-e29b-41d4-a716-446655440001'::uuid, 800.00, 'BDT', 'PENDING', NOW(), NOW()),
--(1017, 109, '550e8400-e29b-41d4-a716-446655440001'::uuid, 115.00, 'BDT', 'PENDING', NOW(), NOW()),  -- Accepted
--(1018, 110, '550e8400-e29b-41d4-a716-446655440003'::uuid, 380.00, 'BDT', 'PENDING', NOW(), NOW()),  -- Accepted
--(1020, 111, '550e8400-e29b-41d4-a716-446655440004'::uuid, 440.00, 'BDT', 'PENDING', NOW(), NOW());  -- Accepted
--
---- Reviews where Asif Iqbal is reviewed as a Buyer (Total 5)
--INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
--(10000, 1001, 'BUYER', 5, 'Great buyer! Prompt payment and excellent communication.', NOW(), NOW()),
--(10001, 1006, 'BUYER', 4, 'Smooth transaction. Thank you!', NOW(), NOW()),
--(10003, 1008, 'BUYER', 4, 'Good buyer. Would sell to again.', NOW(), NOW()),
--(10004, 1009, 'BUYER', 5, 'Excellent buyer! Highly recommended.', NOW(), NOW()),
--(10017, 1016, 'BUYER', 5, 'Asif was prompt and communicative.', NOW(), NOW());
--
---- Reviews where Asif Iqbal is reviewed as a Seller (Total 5)
--INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
--(10005, 1010, 'SELLER', 5, 'Fantastic seller! Item as described.', NOW(), NOW()),
--(10008, 1013, 'SELLER', 5, 'Asif is an excellent seller!', NOW(), NOW()),
--(10018, 1017, 'SELLER', 4, 'Great seller! Product as described.', NOW(), NOW()),
--(10019, 1018, 'SELLER', 5, 'Asif was very helpful throughout.', NOW(), NOW()),
--(10020, 1020, 'SELLER', 5, 'Excellent seller, fast shipping!', NOW(), NOW());
--
---- Reviews where Asif Iqbal reviews other Sellers (Total 3)
--INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
--(10010, 1001, 'SELLER', 5, 'John was a great seller! Highly recommend.', NOW(), NOW()),
--(10011, 1006, 'SELLER', 4, 'Smooth transaction with Jane.', NOW(), NOW()),
--(10021, 1016, 'SELLER', 5, 'Great seller! Product exceeded expectations.', NOW(), NOW());
--
---- Reviews where Asif Iqbal reviews other Buyers (Total 3)
--INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
--(10013, 1010, 'BUYER', 5, 'John was prompt with payment. Great buyer!', NOW(), NOW()),
--(10022, 1017, 'BUYER', 5, 'Jane was a wonderful buyer!', NOW(), NOW()),
--(10023, 1018, 'BUYER', 5, 'Mark was prompt with payment.', NOW(), NOW());