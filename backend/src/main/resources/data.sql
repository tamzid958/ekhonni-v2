-- First, insert Accounts
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
('550e8400-e29b-41d4-a716-446655440005'::uuid, 'Asif Iqbal', 'asif783810@gmail.com', '$2y$10$eYGOYQm9XRLex5KQQuXITecm5FqIqY0tlFq3awFjed8A9MwRAPX5W', '+1234567894', '654 Pine St', 102, 106, true, NOW(), NOW());


-- Insert Categories
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(1, 'Electronics', true, null, NOW(), NOW()),
(2, 'Smartphones', true, 1, NOW(), NOW()),
(3, 'Laptops', true, 1, NOW(), NOW()),
(4, 'Fashion', true, null, NOW(), NOW()),
(5, 'Men''s Clothing', true, 4, NOW(), NOW());

-- Insert Products (now all users can list products)
INSERT INTO product (id, name, price, description, approved, sold, condition, category_id, seller_id, created_at, updated_at) VALUES
(1, 'iPhone 13 Pro', 899.99, 'Slightly used iPhone 13 Pro, 256GB', true, false, 'USED', 2, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW()),
(2, 'MacBook Pro 2022', 1499.99, 'New MacBook Pro M1', true, false, 'NEW', 3, '550e8400-e29b-41d4-a716-446655440001'::uuid, NOW(), NOW()),
(3, 'Samsung Galaxy S21', 699.99, 'Brand new Samsung Galaxy S21', true, false, 'NEW', 2, '550e8400-e29b-41d4-a716-446655440003'::uuid, NOW(), NOW()),
(4, 'Dell XPS 13', 1299.99, 'Dell XPS 13 laptop, slightly used', true, false, 'USED', 3, '550e8400-e29b-41d4-a716-446655440004'::uuid, NOW(), NOW()),
(5, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 5, '550e8400-e29b-41d4-a716-446655440000'::uuid, NOW(), NOW());

-- Insert Bids
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1, 1, '550e8400-e29b-41d4-a716-446655440005'::uuid, 100.00, 'USD', 'ACCEPTED', NOW(), NOW()),
(2, 1, '550e8400-e29b-41d4-a716-446655440003'::uuid, 875.00, 'USD', 'PENDING', NOW(), NOW()),
(3, 2, '550e8400-e29b-41d4-a716-446655440004'::uuid, 1450.00, 'USD', 'PENDING', NOW(), NOW()),
(4, 3, '550e8400-e29b-41d4-a716-446655440000'::uuid, 680.00, 'USD', 'ACCEPTED', NOW(), NOW()),
(5, 4, '550e8400-e29b-41d4-a716-446655440001'::uuid, 1250.00, 'USD', 'REJECTED', NOW(), NOW());