-- Insert Categories
INSERT INTO category (id, name, active, parent_category_id, created_at, updated_at) VALUES
(1, 'Electronics', true, NULL, NOW(), NOW()),
(2, 'Smartphones', true, 1, NOW(), NOW()),
(3, 'Laptops', true, 1, NOW(), NOW()),
(4, 'Home Appliances', true, NULL, NOW(), NOW()),
(5, 'Washing Machines', true, 4, NOW(), NOW());

-- Insert Products
INSERT INTO product (id, name, price, description, approved, sold, condition, category_id, user_id, created_at, updated_at) VALUES
(1, 'iPhone 14', 999, 'Latest Apple smartphone', true, false, 'NEW', 2, '064f3c85-3044-4b56-9972-a1a3187cafff', NOW(), NOW()),
(2, 'Samsung Galaxy S22', 850, 'Flagship Samsung smartphone', true, false, 'NEW', 2, 'fa613306-c166-459f-8754-80ce123952bf', NOW(), NOW()),
(3, 'Dell XPS 13', 1200, 'High-performance laptop', true, false, 'NEW', 3, '5531750f-d4c1-4de9-8f50-be6bf5cf08e5', NOW(), NOW()),
(4, 'LG Washing Machine', 700, 'Energy-efficient washing machine', true, false, 'NEW', 5, 'ef4e702f-a919-47dc-9c5b-342bc2cb5eec', NOW(), NOW()),
(5, 'Sony Headphones', 150, 'Noise-canceling headphones', true, false, 'NEW', 1, '9af1135d-186e-401d-bd3b-a9dceda15b6c', NOW(), NOW());

-- Insert Bids
INSERT INTO bid (id, product_id, bidder_id, amount, currency, status, created_at, updated_at) VALUES
(1, 1, 'dd62c016-4946-4914-8cdf-98747ac93a5c', 980, 'USD', 'PENDING', NOW(), NOW()),
(2, 2, '5a392821-e16e-4b61-b994-29e36323cd1d', 800, 'USD', 'ACCEPTED', NOW(), NOW()),
(3, 3, 'cd71d0cb-7538-429d-8b22-24353d398937', 1100, 'USD', 'PENDING', NOW(), NOW()),
(4, 4, 'a680c8c1-81b9-4322-8751-c25b654f50b4', 650, 'USD', 'ACCEPTED', NOW(), NOW()),
(5, 5, '8e22ed64-83cb-4194-81b1-ce377925a622', 140, 'USD', 'REJECTED', NOW(), NOW());
