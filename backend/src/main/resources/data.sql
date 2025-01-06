-- Insert Categories first (parent category)
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(1, 'Electronics', true, null, NOW(), NOW()),
(2, 'Fashion', true, null, NOW(), NOW()),
(3, 'Home & Garden', true, null, NOW(), NOW());

-- Insert sub-category
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(4, 'Smartphones', true, 1, NOW(), NOW()),
(5, 'Laptops', true, 1, NOW(), NOW()),
(6, 'Men''s Clothing', true, 2, NOW(), NOW()),
(7, 'Women''s Clothing', true, 2, NOW(), NOW()),
(8, 'Furniture', true, 3, NOW(), NOW());

-- Insert Users
INSERT INTO users (id, name, email, password, role, phone, address, created_at, updated_at,verified) VALUES
(gen_random_uuid(), 'John Doe', 'john@example.com', '$2a$10$xxxxxxxxxxx', 'USER', '+1234567890', '123 Main St', NOW(), NOW(),'true'),
(gen_random_uuid(), 'Jane Smith', 'jane@example.com', '$2a$10$xxxxxxxxxxx', 'USER', '+1234567891', '456 Oak Ave', NOW(), NOW(),'true'),
(gen_random_uuid(), 'Admin User', 'admin@example.com', '$2a$10$xxxxxxxxxxx', 'ADMIN', '+1234567892', '789 Admin St', NOW(), NOW(),'true'),
(gen_random_uuid(), 'Sarah Wilson', 'sarah@example.com', '$2a$10$xxxxxxxxxxx', 'USER', '+1234567893', '321 Pine Rd', NOW(), NOW(),'true'),
(gen_random_uuid(), 'Mike Johnson', 'mike@example.com', '$2a$10$xxxxxxxxxxx', 'USER', '+1234567894', '654 Elm St', NOW(), NOW(),'true');

-- Insert Accounts (assuming user IDs from above)
INSERT INTO account (id, balance, status, created_at, updated_at) VALUES
(101, 1000.00, 'ACTIVE', NOW(), NOW()),
(102, 1500.00, 'ACTIVE', NOW(), NOW()),
(103, 2000.00, 'ACTIVE', NOW(), NOW()),
(104, 750.00, 'ACTIVE', NOW(), NOW()),
(105, 1250.00, 'ACTIVE', NOW(), NOW());

-- Update users with account IDs
UPDATE users SET account_id = 101 WHERE email = 'john@example.com';
UPDATE users SET account_id = 102 WHERE email = 'jane@example.com';
UPDATE users SET account_id = 103 WHERE email = 'admin@example.com';
UPDATE users SET account_id = 104 WHERE email = 'sarah@example.com';
UPDATE users SET account_id = 105 WHERE email = 'mike@example.com';

-- Insert Products
INSERT INTO product (id, name, price, description, approved, sold, condition, category_id, seller_id, image_path, created_at, updated_at) VALUES
(1, 'iPhone 13', 699.99, 'Latest iPhone model', true, false, 'NEW', 4, (SELECT id FROM users WHERE email = 'john@example.com'), '/images/iphone13.jpg', NOW(), NOW()),
(2, 'MacBook Pro', 1299.99, 'M1 Chip MacBook', true, false, 'NEW', 5, (SELECT id FROM users WHERE email = 'jane@example.com'), '/images/macbook.jpg', NOW(), NOW()),
(3, 'Leather Jacket', 199.99, 'Genuine leather jacket', true, false, 'NEW', 6, (SELECT id FROM users WHERE email = 'sarah@example.com'), '/images/jacket.jpg', NOW(), NOW()),
(4, 'Designer Dress', 299.99, 'Evening gown', true, false, 'NEW', 7, (SELECT id FROM users WHERE email = 'mike@example.com'), '/images/dress.jpg', NOW(), NOW()),
(5, 'Sofa Set', 899.99, 'Modern 3-piece sofa', true, false, 'NEW', 8, (SELECT id FROM users WHERE email = 'john@example.com'), '/images/sofa.jpg', NOW(), NOW());

-- Insert Bids
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1, 1, (SELECT id FROM users WHERE email = 'jane@example.com'), 680.00, 'BDT', 'ACCEPTED', NOW(), NOW()),
(2, 1, (SELECT id FROM users WHERE email = 'sarah@example.com'), 685.00, 'USD', 'ACCEPTED', NOW(), NOW()),
(3, 2, (SELECT id FROM users WHERE email = 'mike@example.com'), 1250.00, 'USD', 'PENDING', NOW(), NOW()),
(4, 3, (SELECT id FROM users WHERE email = 'john@example.com'), 180.00, 'USD', 'PENDING', NOW(), NOW()),
(5, 4, (SELECT id FROM users WHERE email = 'sarah@example.com'), 280.00, 'USD', 'PENDING', NOW(), NOW());