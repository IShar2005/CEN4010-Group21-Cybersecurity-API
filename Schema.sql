-- ============================================
-- Geek Text Bookstore - Profile Management
-- Database Schema
-- ============================================

CREATE TABLE users (
                       username        VARCHAR(50)  PRIMARY KEY,
                       password        VARCHAR(255) NOT NULL,
                       first_name      VARCHAR(50),
                       last_name       VARCHAR(50),
                       email_address   VARCHAR(100) UNIQUE,
                       home_address    VARCHAR(255),
                       created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE credit_cards (
                              id              SERIAL PRIMARY KEY,
                              username        VARCHAR(50) NOT NULL REFERENCES users(username) ON DELETE CASCADE,
                              card_number     VARCHAR(20) NOT NULL,
                              card_holder     VARCHAR(100),
                              expiration_date VARCHAR(7),  -- Format: MM/YYYY
                              cvv             VARCHAR(4),
                              created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
