-- Insert Accounts
INSERT INTO account (id, balance, status, created_at, updated_at) VALUES
(101, 1000.00, 'ACTIVE', NOW(), NOW()),
(102, 750.50, 'ACTIVE', NOW(), NOW()),
(103, 5000.00, 'ACTIVE', NOW(), NOW()),
(104, 2500.00, 'ACTIVE', NOW(), NOW()),
(105, 1800.00, 'ACTIVE', NOW(), NOW()),
(106, 1800.00, 'ACTIVE', NOW(), NOW());

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
('550e8400-e29b-41d4-a716-446655440005'::uuid, 'Asif Iqbal', 'asif783810@gmail.com', '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567894', 'Dhamrai, Dhaka', 102, 106, true, NOW(), NOW());

-- Insert Categories
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(1, 'Electronics', true, null, NOW(), NOW()),
(2, 'Smartphones', true, 1, NOW(), NOW()),
(3, 'Laptops', true, 1, NOW(), NOW()),
(4, 'Fashion', true, null, NOW(), NOW()),
(5, 'Men''s Clothing', true, 4, NOW(), NOW());

-- Insert Products
INSERT INTO product (id, name, price, description, approved, sold, condition, category_id, seller_id, created_at, updated_at) VALUES
(1, 'iPhone 13 Pro', 899.99, 'Slightly used iPhone 13 Pro, 256GB', true, false, 'USED', 2, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW()),
(2, 'MacBook Pro 2022', 1499.99, 'New MacBook Pro M1', true, false, 'NEW', 3, '550e8400-e29b-41d4-a716-446655440001'::uuid, NOW(), NOW()),
(3, 'Samsung Galaxy S21', 699.99, 'Brand new Samsung Galaxy S21', true, false, 'NEW', 2, '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
(4, 'Dell XPS 13', 1299.99, 'Dell XPS 13 laptop, slightly used', true, false, 'USED', 3, '550e8400-e29b-41d4-a716-446655440004'::uuid, NOW(), NOW()),
(5, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW()),
(6, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
(7, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
(8, 'Sony WH-1000XM4 Headphones', 350.00, 'Noise-canceling headphones', true, false, 'NEW', 1, '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
(9, 'Nike Air Max', 120.00, 'Brand new running shoes', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
(10, 'Apple Watch Series 6', 399.00, 'Used but in great condition', true, false, 'USED', 1, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW()),
(11, 'Canon EOS Rebel T7', 450.00, 'Digital SLR Camera', true, false, 'NEW', 1, '550e8400-e29b-41d4-a716-446655440005'::uuid, NOW(), NOW());

-- Insert Bids
-- Bids where Asif is the Bidder (Buyer)
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1001, 1, '550e8400-e29b-41d4-a716-446655440005'::uuid, 100.00, 'USD', 'ACCEPTED', NOW(), NOW()),  -- Accepted
(1002, 1, '550e8400-e29b-41d4-a716-446655440003'::uuid, 875.00, 'USD', 'PENDING', NOW(), NOW()),    -- Pending
(1006, 2, '550e8400-e29b-41d4-a716-446655440005'::uuid, 150.00, 'USD', 'ACCEPTED', NOW(), NOW()),   -- Accepted
(1003, 2, '550e8400-e29b-41d4-a716-446655440004'::uuid, 1450.00, 'USD', 'PENDING', NOW(), NOW()),   -- Pending
(1008, 4, '550e8400-e29b-41d4-a716-446655440005'::uuid, 250.00, 'USD', 'ACCEPTED', NOW(), NOW()),   -- Accepted
(1009, 5, '550e8400-e29b-41d4-a716-446655440005'::uuid, 300.00, 'USD', 'ACCEPTED', NOW(), NOW()),   -- Accepted
(1016, 8, '550e8400-e29b-41d4-a716-446655440005'::uuid, 340.00, 'USD', 'ACCEPTED', NOW(), NOW());   -- Accepted

-- Bids where Asif is the Seller
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1010, 6, '550e8400-e29b-41d4-a716-446655440000'::uuid, 1000.00, 'USD', 'ACCEPTED', NOW(), NOW()),  -- Accepted
(1011, 6, '550e8400-e29b-41d4-a716-446655440001'::uuid, 800.00, 'USD', 'REJECTED', NOW(), NOW()),
(1013, 7, '550e8400-e29b-41d4-a716-446655440000'::uuid, 1000.00, 'USD', 'ACCEPTED', NOW(), NOW()),  -- Accepted
(1014, 7, '550e8400-e29b-41d4-a716-446655440001'::uuid, 800.00, 'USD', 'REJECTED', NOW(), NOW()),
(1017, 9, '550e8400-e29b-41d4-a716-446655440001'::uuid, 115.00, 'USD', 'ACCEPTED', NOW(), NOW()),   -- Accepted
(1018, 10, '550e8400-e29b-41d4-a716-446655440003'::uuid, 380.00, 'USD', 'ACCEPTED', NOW(), NOW()),  -- Accepted
(1020, 11, '550e8400-e29b-41d4-a716-446655440004'::uuid, 440.00, 'USD', 'ACCEPTED', NOW(), NOW());  -- Accepted

-- Insert Reviews
-- Reviews where Asif Iqbal is reviewed as a Buyer (Total 5)
INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
(10000, 1001, 'BUYER', 5, 'Great buyer! Prompt payment and excellent communication.', NOW(), NOW()),
(10001, 1006, 'BUYER', 4, 'Smooth transaction. Thank you!', NOW(), NOW()),
(10003, 1008, 'BUYER', 4, 'Good buyer. Would sell to again.', NOW(), NOW()),
(10004, 1009, 'BUYER', 5, 'Excellent buyer! Highly recommended.', NOW(), NOW()),
(10017, 1016, 'BUYER', 5, 'Asif was prompt and communicative.', NOW(), NOW());

-- Reviews where Asif Iqbal is reviewed as a Seller (Total 5)
INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
(10005, 1010, 'SELLER', 5, 'Fantastic seller! Item as described.', NOW(), NOW()),
(10008, 1013, 'SELLER', 5, 'Asif is an excellent seller!', NOW(), NOW()),
(10018, 1017, 'SELLER', 4, 'Great seller! Product as described.', NOW(), NOW()),
(10019, 1018, 'SELLER', 5, 'Asif was very helpful throughout.', NOW(), NOW()),
(10020, 1020, 'SELLER', 5, 'Excellent seller, fast shipping!', NOW(), NOW());

-- Reviews where Asif Iqbal reviews other Sellers (Total 3)
INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
(10010, 1001, 'SELLER', 5, 'John was a great seller! Highly recommend.', NOW(), NOW()),
(10011, 1006, 'SELLER', 4, 'Smooth transaction with Jane.', NOW(), NOW()),
(10021, 1016, 'SELLER', 5, 'Great seller! Product exceeded expectations.', NOW(), NOW());

-- Reviews where Asif Iqbal reviews other Buyers (Total 3)
INSERT INTO review (id, bid_id, type, rating, description, created_at, updated_at) VALUES
(10013, 1010, 'BUYER', 5, 'John was prompt with payment. Great buyer!', NOW(), NOW()),
(10022, 1017, 'BUYER', 5, 'Jane was a wonderful buyer!', NOW(), NOW()),
(10023, 1018, 'BUYER', 5, 'Mark was prompt with payment.', NOW(), NOW());