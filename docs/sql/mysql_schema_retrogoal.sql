-- RetroGoal MySQL schema
-- Ejecutar en MySQL Workbench o consola MySQL.
-- Usuario admin inicial:
--   email: admin@retrogoal.com
--   password: admin123

CREATE DATABASE IF NOT EXISTS retrogoal
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE retrogoal;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(120),
    first_name VARCHAR(120),
    last_name VARCHAR(160),
    phone VARCHAR(40),
    INDEX idx_users_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    team VARCHAR(120),
    name_en VARCHAR(255),
    description_en TEXT,
    team_en VARCHAR(120),
    name_fr VARCHAR(255),
    description_fr TEXT,
    team_fr VARCHAR(120),
    era VARCHAR(50),
    size VARCHAR(20),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(255),
    league VARCHAR(120),
    league_en VARCHAR(120),
    league_fr VARCHAR(120),
    retro BIT NOT NULL DEFAULT 0,
    INDEX idx_products_team (team),
    INDEX idx_products_league (league),
    INDEX idx_products_era (era),
    INDEX idx_products_retro (retro),
    INDEX idx_products_price (price)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS product_recommendations (
    product_id BIGINT NOT NULL,
    recommended_product_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, recommended_product_id),
    CONSTRAINT fk_recommendations_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_recommendations_recommended FOREIGN KEY (recommended_product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    status VARCHAR(50),
    order_date DATETIME,
    total_price DECIMAL(10,2),
    shipping_address VARCHAR(255),
    INDEX idx_orders_user (user_id),
    INDEX idx_orders_status (status),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10,2),
    INDEX idx_order_items_order (order_id),
    INDEX idx_order_items_product (product_id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    UNIQUE KEY uk_cart_user_product (user_id, product_id),
    INDEX idx_cart_user (user_id),
    CONSTRAINT fk_cart_items_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_items_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS recent_searches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    search_query VARCHAR(120) NOT NULL,
    searched_at DATETIME NOT NULL,
    INDEX idx_recent_search_user_date (user_id, searched_at),
    CONSTRAINT fk_recent_search_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT IGNORE INTO roles (id, name)
VALUES 
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- Usuario administrador
-- Email: hectorrf.correo@gmail.com
-- Contraseña: Goatclover
INSERT INTO users (email, password, name, first_name, last_name, phone)
VALUES (
    'hectorrf.correo@gmail.com',
    '$2y$10$8xcCuY.eESyWXlLynWqSGO8q2oy5hiWszKu6gRU3H1g0Hs.FWEUam',
    'Héctor Admin',
    'Héctor',
    'Admin',
    NULL
)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    name = VALUES(name),
    first_name = VALUES(first_name),
    last_name = VALUES(last_name),
    phone = VALUES(phone);

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'hectorrf.correo@gmail.com';

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'hectorrf.correo@gmail.com';

SELECT u.email, r.name AS role_name
FROM users u
JOIN users_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.email = 'hectorrf.correo@gmail.com';