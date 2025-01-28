-- First, insert Accounts
INSERT INTO account (id, balance, status, created_at, updated_at) VALUES
(101, 1000.00, 'ACTIVE', NOW(), NOW()),
(102, 750.50, 'ACTIVE', NOW(), NOW()),
(103, 5000.00, 'ACTIVE', NOW(), NOW()),
(104, 2500.00, 'ACTIVE', NOW(), NOW()),
(105, 1800.00, 'ACTIVE', NOW(), NOW()),
(106, 1800.00, 'ACTIVE', NOW(), NOW()),
(107, 1800.00, 'ACTIVE', NOW(), NOW());

-- Insert Roles (only ADMIN and USER now)
INSERT INTO role (id, name) VALUES
(101, 'ADMIN'),
(102, 'USER');

-- Insert Users (now all regular users except one admin)
INSERT INTO users (id, name, email, password, phone, address, role_id, account_id, verified, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440000'::uuid, 'John Doe', 'john@example.com', '$2a$10$encrypted1', '+1234567890', '123 Main St', 102, 101, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440001'::uuid, 'Jane Smith', 'jane@example.com', '$2a$10$encrypted2', '+1234567891', '456 Oak Ave', 102, 102, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440002'::uuid, 'Admin User', 'admin@example.com', '$2a$10$encrypted3', '+1234567892', '789 Admin St', 101, 103, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440003'::uuid, 'Mark Wilson', 'mark@example.com', '$2a$10$encrypted4', '+1234567893', '321 Oak Ave', 102, 104, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440004'::uuid, 'Sarah Brown', 'sarah@example.com', '$2a$10$encrypted5', '+1234567894', '654 Pine St', 102, 105, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440006'::uuid, 'Rifat Shariar Sakil', 'shariarsakil101@gmail.com', '$2a$12$Sdg6oZNAi0XulmEeJluSfu6HGG43mIoH0kCXlli6CJinjnSToY3km', '+1234567894', '654 Pine St', 102, 106, true, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440005'::uuid, 'Asif Iqbal', 'asif783810@gmail.com', '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567894', '654 Pine St', 102, 107, true, NOW(), NOW());

--
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
----(1, 'All', true, null, NOW(), NOW()),
--(2, 'Travel & Nature', true, null, NOW(), NOW()),
--(3, 'Antiques', true, null, NOW(), NOW()),
--(4, 'Health & Beauty', true, null, NOW(), NOW()),
--(5, 'Sports & Outdoors', true, null, NOW(), NOW()),
--(6, 'Toys & Games', true, null, NOW(), NOW()),
--(7, 'Automotive', true, null, NOW(), NOW()),
--(8, 'Books & Stationery', true, null, NOW(), NOW()),
--(9, 'Groceries', true, null, NOW(), NOW()),
--(10, 'Office Supplies', true, null, NOW(), NOW());
--
--
----insert into Travel and Nature
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(11, 'Adventure Travel', true, 2, NOW(), NOW()),
--(12, 'Camping & Hiking', true, 2, NOW(), NOW()),
--(13, 'Wildlife & Safari', true, 2, NOW(), NOW()),
--(14, 'Eco-Tourism', true, 2, NOW(), NOW()),
--(15, 'Beach Holidays', true, 2, NOW(), NOW()),
--(16, 'Cultural Tours', true, 2, NOW(), NOW());
--
--
----insert into Adventure Travel
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(17, 'Mountain Climbing', true, 11, NOW(), NOW()),
--(18, 'Rock Climbing', true, 11, NOW(), NOW()),
--(19, 'Trekking', true, 11, NOW(), NOW()),
--(20, 'Canoeing & Kayaking', true, 11, NOW(), NOW()),
--(21, 'Desert Expeditions', true, 11, NOW(), NOW()),
--(22, 'Polar Expeditions', true, 11, NOW(), NOW());
--
--
---- Insert subcategories into Antiques
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(23, 'Furniture', true, 3, NOW(), NOW()),
--(24, 'Jewelry', true, 3, NOW(), NOW()),
--(25, 'Coins & Currency', true, 3, NOW(), NOW()),
--(26, 'Instruments & Tools', true, 3, NOW(), NOW()),
--(27, 'Watches & Clocks', true, 3, NOW(), NOW()),
--(28, 'Clothing', true, 3, NOW(), NOW());
--
--
---- Insert into Health & Beauty
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(29, 'Skincare', true, 4, NOW(), NOW()),
--(30, 'Hair Care', true, 4, NOW(), NOW()),
--(31, 'Makeup', true, 4, NOW(), NOW()),
--(32, 'Fragrances', true, 4, NOW(), NOW()),
--(33, 'Personal Hygiene', true, 4, NOW(), NOW()),
--(34, 'Health Supplements', true, 4, NOW(), NOW());
--
---- Insert into Sports & Outdoors
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(35, 'Fitness Equipment', true, 5, NOW(), NOW()),
--(36, 'Water Sports', true, 5, NOW(), NOW()),
--(37, 'Team Sports', true, 5, NOW(), NOW()),
--(38, 'Winter Sports', true, 5, NOW(), NOW()),
--(39, 'Fishing', true, 5, NOW(), NOW()),
--(40, 'Cycling', true, 5, NOW(), NOW());
--
---- Insert into Toys & Games
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(41, 'Board Games', true, 6, NOW(), NOW()),
--(42, 'Action Figures', true, 6, NOW(), NOW()),
--(43, 'Dolls & Plush Toys', true, 6, NOW(), NOW()),
--(44, 'Building Toys', true, 6, NOW(), NOW()),
--(45, 'Educational Toys', true, 6, NOW(), NOW()),
--(46, 'Outdoor Toys', true, 6, NOW(), NOW());
--
---- Insert into Automotive
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(47, 'Car Accessories', true, 7, NOW(), NOW()),
--(48, 'Motorcycle Accessories', true, 7, NOW(), NOW()),
--(49, 'Car Care', true, 7, NOW(), NOW()),
--(50, 'Auto Electronics', true, 7, NOW(), NOW()),
--(51, 'Replacement Parts', true, 7, NOW(), NOW()),
--(52, 'Tires & Wheels', true, 7, NOW(), NOW());
--
---- Insert into Books & Stationery
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(53, 'Fiction', true, 8, NOW(), NOW()),
--(54, 'Non-Fiction', true, 8, NOW(), NOW()),
--(55, 'Academic Books', true, 8, NOW(), NOW()),
--(56, 'Notebooks & Diaries', true, 8, NOW(), NOW()),
--(57, 'Art Supplies', true, 8, NOW(), NOW()),
--(58, 'Office Stationery', true, 8, NOW(), NOW());
--
---- Insert into Groceries
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(59, 'Fruits & Vegetables', true, 9, NOW(), NOW()),
--(60, 'Meat & Seafood', true, 9, NOW(), NOW()),
--(61, 'Dairy Products', true, 9, NOW(), NOW()),
--(62, 'Beverages', true, 9, NOW(), NOW()),
--(63, 'Snacks', true, 9, NOW(), NOW()),
--(64, 'Pantry Staples', true, 9, NOW(), NOW());
--
---- Insert into Office Supplies
--INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
--(65, 'Desk Accessories', true, 10, NOW(), NOW()),
--(66, 'Office Furniture', true, 10, NOW(), NOW()),
--(67, 'Writing Instruments', true, 10, NOW(), NOW()),
--(68, 'Paper Products', true, 10, NOW(), NOW()),
--(69, 'Printing Supplies', true, 10, NOW(), NOW()),
--(70, 'Organizers', true, 10, NOW(), NOW());

-- Insert into category
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





-- Insert data into product
--
--
--INSERT INTO product (id, name, price, description, location, status, condition, category_id, seller_id, created_at, updated_at) VALUES
--(202, 'Shawl', 3000.00, 'An antique shawl', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10026, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
--(303, 'Tea Pot', 1200.00, 'An antique teapot', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
--(304, 'Watch', 1200.00, 'An antique watch', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10025, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
--(305, 'Marble', 1200.00, 'Antique marbles', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
--(306, 'Keyboard', 56000.00, 'An antique keyboard', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10001, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
--(352, 'Oil', 56000.00, 'Oil For hair', 'Dhaka', 'PENDING_APPROVAL', 'GOOD', 10028, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW());
--


INSERT INTO product
    (id, title, sub_title, description, price, division, location, status, condition, condition_details, category_id, seller_id, created_at, updated_at)
VALUES
    (202, 'Shawl', 'Beautiful Antique Shawl', 'A hand-crafted antique shawl', 3000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Minor wear and tear', 10026, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
    (303, 'Tea Pot', 'Antique Tea Pot', 'A rare antique teapot made of porcelain', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Slight chipping on the lid', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
    (304, 'Watch', 'Classic Antique Watch', 'A vintage watch with a leather strap', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Scratches on the glass', 10025, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
    (305, 'Marble', 'Antique Marble Collection', 'A set of antique marbles from the 19th century', 1200.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Some discoloration', 10024, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
    (306, 'Keyboard', 'Rare Antique Keyboard', 'A rare typewriter-style keyboard', 56000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Fully functional', 10001, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
    (352, 'Oil', 'Vintage Hair Oil', 'A rare bottle of vintage hair oil', 56000.00, 'DHAKA', 'Dhaka', 'PENDING_APPROVAL', 'Good', 'Original packaging intact', 10028, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW());



-- Insert into ProductImage table
INSERT INTO product_image (id, product_id, image_path, created_at, updated_at) VALUES
(1, 202, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031662/uqoduxx5ewvnchgsuols.jpg', NOW(), NOW()),
(2, 202, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737031664/mfmihqj34q8p0zuuiekg.jpg', NOW(), NOW()),
(3, 303, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269109/hqrufykrmzwv9wq5xwyv.jpg', NOW(), NOW()),
(4, 303, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269112/x7htnhtwlw7uhvzuvhmi.jpg', NOW(), NOW()),
(5, 304, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269197/vldssebhqijs41pjanio.jpg', NOW(), NOW()),
(6, 304, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269198/wvld1ck8cwdmldbfhage.jpg', NOW(), NOW()),
(7, 305, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269363/dr2slp65tsk9z2n9rcs9.jpg', NOW(), NOW()),
(8, 305, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269364/sy87ffhehxuffkrczp8w.jpg', NOW(), NOW()),
(9, 306, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269460/plwi5lonfnwb4mvarfpy.jpg', NOW(), NOW()),
(10, 306, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737269462/xpwozjsexnldymbyal1c.jpg', NOW(), NOW()),
(11, 352, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284715/pjgaqxks4wrh34civzle.jpg', NOW(), NOW()),
(12, 352, 'http://res.cloudinary.com/dnetpmsx6/image/upload/v1737284716/gnoxqfkpgcgvly3yjbfa.jpg', NOW(), NOW());
--
--

---- Insert Products (now all users can list products)
--INSERT INTO product (id, name, price, description, approved, sold, condition, category_id, seller_id, created_at, updated_at) VALUES
--(1, 'iPhone 13 Pro', 899.99, 'Slightly used iPhone 13 Pro, 256GB', true, false, 'USED', 2, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW()),
--(2, 'MacBook Pro 2022', 1499.99, 'New MacBook Pro M1', true, false, 'NEW', 3, '550e8400-e29b-41d4-a716-446655440001'::uuid, NOW(), NOW()),
--(3, 'Samsung Galaxy S21', 699.99, 'Brand new Samsung Galaxy S21', true, false, 'NEW', 2, '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
--(4, 'Dell XPS 13', 1299.99, 'Dell XPS 13 laptop, slightly used', true, false, 'USED', 3, '550e8400-e29b-41d4-a716-446655440004'::uuid, NOW(), NOW()),
--(5, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW());
--
---- Insert Bids
--INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
--(1, 1, '550e8400-e29b-41d4-a716-446655440005'::uuid, 850.00, 'BDT', 'ACCEPTED', NOW(), NOW()),
--(2, 1, '550e8400-e29b-41d4-a716-446655440003'::uuid, 875.00, 'USD', 'PENDING', NOW(), NOW()),
--(3, 2, '550e8400-e29b-41d4-a716-446655440004'::uuid, 1450.00, 'USD', 'PENDING', NOW(), NOW()),
--(4, 3, '550e8400-e29b-41d4-a716-446655440000'::uuid, 680.00, 'USD', 'ACCEPTED', NOW(), NOW()),
--(5, 4, '550e8400-e29b-41d4-a716-446655440001'::uuid, 1250.00, 'USD', 'REJECTED', NOW(), NOW());