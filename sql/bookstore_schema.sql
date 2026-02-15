-- ============================================================
-- CEN 4010 - Group 21 Bookstore REST API
-- Unified Database Schema + Seed Data
-- Sprint 2 - Database Design and Data Seeding
-- ============================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

-- ============================================================
-- TABLE: author
-- Stores author information (linked to books via book_author)
-- ============================================================
CREATE TABLE author (
    author_id   INT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    biography   TEXT,
    publisher   VARCHAR(100)
);

-- ============================================================
-- TABLE: book
-- Central entity - all features reference this table
-- ============================================================
CREATE TABLE book (
    book_id         INT AUTO_INCREMENT PRIMARY KEY,
    isbn            VARCHAR(13)    NOT NULL UNIQUE,
    title           VARCHAR(200)   NOT NULL,
    description     TEXT,
    price           DECIMAL(10,2)  NOT NULL,
    genre           VARCHAR(50),
    publisher       VARCHAR(100),
    year_published  INT,
    copies_sold     INT            DEFAULT 0,
    average_rating  DECIMAL(3,2)   DEFAULT 0.00
);

-- ============================================================
-- TABLE: book_author (Junction table - Many-to-Many)
-- A book can have multiple authors, an author can write many books
-- ============================================================
CREATE TABLE book_author (
    book_id    INT NOT NULL,
    author_id  INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id)   REFERENCES book(book_id)   ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: user
-- Profile Management (Rosa) - stores user account info
-- ============================================================
CREATE TABLE user (
    user_id    INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    address    VARCHAR(255),
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: credit_card
-- Profile Management (Rosa) - users can have multiple cards
-- ============================================================
CREATE TABLE credit_card (
    card_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT          NOT NULL,
    card_number VARCHAR(19)  NOT NULL,
    expiry_date VARCHAR(5)   NOT NULL,
    cvv         VARCHAR(4)   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: shopping_cart
-- Shopping Cart (Isa) - one cart per user
-- ============================================================
CREATE TABLE shopping_cart (
    cart_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: cart_item
-- Shopping Cart (Isa) - items in a user's cart
-- ============================================================
CREATE TABLE cart_item (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id      INT NOT NULL,
    book_id      INT NOT NULL,
    quantity     INT NOT NULL DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES shopping_cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(book_id)          ON DELETE CASCADE,
    UNIQUE KEY unique_cart_book (cart_id, book_id)
);

-- ============================================================
-- TABLE: wish_list
-- Wish List Management (Sebastian) - one list per user
-- ============================================================
CREATE TABLE wish_list (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT          NOT NULL UNIQUE,
    name        VARCHAR(100) DEFAULT 'My Wish List',
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: wish_list_item
-- Wish List Management (Sebastian) - books in a user's wish list
-- ============================================================
CREATE TABLE wish_list_item (
    wishlist_item_id INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id      INT NOT NULL,
    book_id          INT NOT NULL,
    added_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wishlist_id) REFERENCES wish_list(wishlist_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id)     REFERENCES book(book_id)          ON DELETE CASCADE,
    UNIQUE KEY unique_wishlist_book (wishlist_id, book_id)
);

-- ============================================================
-- TABLE: rating
-- Book Rating and Commenting (Cade) - user ratings for books
-- ============================================================
CREATE TABLE rating (
    rating_id  INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    book_id    INT NOT NULL,
    score      INT NOT NULL CHECK (score BETWEEN 1 AND 5),
    review     TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_book_rating (user_id, book_id)
);

-- ============================================================
-- TABLE: comment
-- Book Rating and Commenting (Cade) - comments on books
-- ============================================================
CREATE TABLE comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT  NOT NULL,
    book_id    INT  NOT NULL,
    content    TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
);


-- ============================================================
-- SEED DATA
-- ============================================================

-- Authors
INSERT INTO author (first_name, last_name, biography, publisher) VALUES
('George',   'Orwell',      'English novelist and essayist known for his sharp criticism of political oppression.',  'Secker & Warburg'),
('Harper',   'Lee',         'American novelist best known for her Pulitzer Prize-winning work.',                      'J.B. Lippincott'),
('F. Scott', 'Fitzgerald',  'American novelist widely regarded as one of the greatest writers of the 20th century.',  'Charles Scribners Sons'),
('Jane',     'Austen',      'English novelist known for her commentary on the British landed gentry.',               'T. Egerton'),
('J.R.R.',   'Tolkien',     'English writer and philologist, author of high fantasy works.',                          'Allen & Unwin'),
('Mary',     'Shelley',     'English novelist who wrote one of the earliest science fiction novels.',                 'Lackington Hughes'),
('Mark',     'Twain',       'American writer, humorist, and lecturer.',                                              'Charles L. Webster'),
('Herman',   'Melville',    'American novelist and poet of the American Renaissance period.',                         'Harper & Brothers'),
('Ray',      'Bradbury',    'American author and screenwriter known for his dystopian works.',                        'Ballantine Books'),
('Aldous',   'Huxley',      'English writer and philosopher.',                                                       'Chatto & Windus');

-- Books
INSERT INTO book (isbn, title, description, price, genre, publisher, year_published, copies_sold, average_rating) VALUES
('9780451524935', '1984',                        'A dystopian novel set in a totalitarian society.',                      9.99,  'Dystopian',        'Secker & Warburg',       1949, 50000, 4.70),
('9780061120084', 'To Kill a Mockingbird',       'A novel about racial injustice in the Deep South.',                    12.99,  'Fiction',           'J.B. Lippincott',        1960, 45000, 4.80),
('9780743273565', 'The Great Gatsby',            'A story of the mysteriously wealthy Jay Gatsby.',                      10.99,  'Classic',           'Charles Scribners Sons',  1925, 35000, 4.50),
('9780141439518', 'Pride and Prejudice',         'A romantic novel following Elizabeth Bennet.',                         8.99,   'Romance',           'T. Egerton',              1813, 40000, 4.60),
('9780547928227', 'The Hobbit',                  'A fantasy novel about the adventure of Bilbo Baggins.',               14.99,  'Fantasy',           'Allen & Unwin',           1937, 60000, 4.75),
('9780486282114', 'Frankenstein',                'A novel about a young scientist who creates a sapient creature.',      7.99,   'Science Fiction',   'Lackington Hughes',       1818, 25000, 4.30),
('9780142437179', 'The Adventures of Tom Sawyer','A novel about a young boy growing up along the Mississippi River.',    6.99,   'Adventure',         'Charles L. Webster',      1876, 30000, 4.20),
('9780142437247', 'Moby-Dick',                   'The saga of Captain Ahab and his obsessive quest.',                   11.99,  'Adventure',         'Harper & Brothers',       1851, 20000, 4.10),
('9781451673319', 'Fahrenheit 451',              'A dystopian novel about a future where books are outlawed.',           9.49,   'Dystopian',         'Ballantine Books',        1953, 38000, 4.55),
('9780060850524', 'Brave New World',             'A dystopian novel anticipating developments in technology.',           10.49,  'Dystopian',         'Chatto & Windus',         1932, 42000, 4.40),
('9780547928210', 'The Lord of the Rings',       'An epic high-fantasy novel following the quest to destroy the One Ring.', 22.99, 'Fantasy',        'Allen & Unwin',           1954, 70000, 4.90),
('9780141439587', 'Sense and Sensibility',       'A novel following the Dashwood sisters after their fathers death.',    8.49,   'Romance',           'T. Egerton',              1811, 18000, 4.25);

-- Book-Author relationships
INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),   -- 1984 by George Orwell
(2, 2),   -- To Kill a Mockingbird by Harper Lee
(3, 3),   -- The Great Gatsby by F. Scott Fitzgerald
(4, 4),   -- Pride and Prejudice by Jane Austen
(5, 5),   -- The Hobbit by J.R.R. Tolkien
(6, 6),   -- Frankenstein by Mary Shelley
(7, 7),   -- Adventures of Tom Sawyer by Mark Twain
(8, 8),   -- Moby-Dick by Herman Melville
(9, 9),   -- Fahrenheit 451 by Ray Bradbury
(10, 10), -- Brave New World by Aldous Huxley
(11, 5),  -- The Lord of the Rings by J.R.R. Tolkien
(12, 4);  -- Sense and Sensibility by Jane Austen

-- Users
INSERT INTO user (username, password, first_name, last_name, email, address) VALUES
('jdoe',        'hashed_password_1', 'John',    'Doe',     'jdoe@email.com',        '123 Main St, Miami, FL'),
('jsmith',      'hashed_password_2', 'Jane',    'Smith',   'jsmith@email.com',      '456 Oak Ave, Miami, FL'),
('mwilson',     'hashed_password_3', 'Mike',    'Wilson',  'mwilson@email.com',     '789 Pine Rd, Miami, FL'),
('abrown',      'hashed_password_4', 'Alice',   'Brown',   'abrown@email.com',      '321 Elm St, Miami, FL'),
('bjohnson',    'hashed_password_5', 'Bob',     'Johnson', 'bjohnson@email.com',    '654 Maple Dr, Miami, FL');

-- Credit Cards
INSERT INTO credit_card (user_id, card_number, expiry_date, cvv) VALUES
(1, '4111111111111111', '12/27', '123'),
(1, '5500000000000004', '06/28', '456'),
(2, '4012888888881881', '03/27', '789'),
(3, '378282246310005',  '09/28', '012');

-- Shopping Carts
INSERT INTO shopping_cart (user_id) VALUES (1), (2), (3), (4), (5);

-- Cart Items
INSERT INTO cart_item (cart_id, book_id, quantity) VALUES
(1, 1, 1),   -- John has 1984
(1, 5, 2),   -- John has 2 copies of The Hobbit
(2, 3, 1),   -- Jane has The Great Gatsby
(2, 4, 1),   -- Jane has Pride and Prejudice
(3, 9, 1);   -- Mike has Fahrenheit 451

-- Wish Lists
INSERT INTO wish_list (user_id, name) VALUES
(1, 'Johns Reading List'),
(2, 'Janes Favorites'),
(3, 'Mikes To-Read'),
(4, 'Alices Picks');

-- Wish List Items
INSERT INTO wish_list_item (wishlist_id, book_id) VALUES
(1, 2),   -- John wants To Kill a Mockingbird
(1, 10),  -- John wants Brave New World
(2, 5),   -- Jane wants The Hobbit
(2, 11),  -- Jane wants The Lord of the Rings
(3, 6),   -- Mike wants Frankenstein
(4, 7);   -- Alice wants Tom Sawyer

-- Ratings
INSERT INTO rating (user_id, book_id, score, review) VALUES
(1, 1, 5, 'An incredible and thought-provoking read.'),
(1, 5, 4, 'Great adventure story, loved the world-building.'),
(2, 3, 5, 'Beautifully written, captures the era perfectly.'),
(2, 4, 4, 'Witty and charming. A timeless classic.'),
(3, 9, 5, 'Terrifyingly relevant to this day.'),
(3, 1, 4, 'Dark but important. Everyone should read this.'),
(4, 2, 5, 'One of the most important novels ever written.'),
(4, 10, 3, 'Interesting concepts but a bit slow in parts.'),
(5, 11, 5, 'The greatest fantasy epic of all time.');

-- Comments
INSERT INTO comment (user_id, book_id, content) VALUES
(1, 1, 'The concept of Big Brother feels more relevant than ever.'),
(2, 1, 'I agree, this book should be required reading.'),
(3, 5, 'The riddles in the dark chapter is my favorite part.'),
(4, 3, 'The green light symbolism is fascinating.'),
(5, 9, 'The firemen burning books is such a powerful image.'),
(1, 11, 'Cannot wait to re-read the trilogy this summer.'),
(2, 4, 'Mr. Darcy is the original rom-com lead!'),
(3, 2, 'Atticus Finch is the hero we all need.');
