# Shopping Cart API - Developer Docs
**Isa Sharief | CEN 4010 Group 21**

## What This Is

This doc explains how the Shopping Cart feature works. It covers the database, the endpoints, and how to run it locally. If you are a teammate or grader, you should be able to read this and understand my feature without looking at my code.

## How the Database Is Set Up

There are three tables that matter for the shopping cart.

First, there's the `shopping_cart` table. Each row is one cart. Each cart belongs to one user.

Then there's the `cart_item` table. Each row is one book inside a cart. So if my cart has 3 books, that's 3 rows in this table.

Finally, the `book` table. This stores all the book info like title, price, and genre. The cart items point to this table to grab the book details.

## How They Connect

A user has one cart. A cart has many items. Each item points to one book.

So the flow goes: User → ShoppingCart → CartItem → Book.

## The Endpoints

I built 5 endpoints total. Here they are:

### 1. Get a user's cart
**URL:** `GET /api/cart/{userId}`

This returns the cart for a specific user with all the items inside it. Each item shows the full book details.

Example response:
```json
{
  "cartId": 1,
  "userId": 1,
  "createdAt": "2026-02-13T18:25:21",
  "cartItems": [
    {
      "cartItemId": 1,
      "book": {
        "bookId": 1,
        "title": "1984",
        "price": 9.99,
        "genre": "Dystopian"
      },
      "quantity": 1
    }
  ]
}
```

If the user has no cart, you get a 404.

### 2. Get all carts
**URL:** `GET /api/cart`

This returns every cart in the database. I made this one mainly for testing so I could see everything at once.

### 3. Add a book to a cart
**URL:** `POST /api/cart/{userId}/items`

This adds a book to the user's cart. Send a JSON body with the book ID and quantity:
```json
{ "bookId": 3, "quantity": 1 }
```

A few things to know about this one. If the user doesn't have a cart yet, one is created automatically. Also, if the book is already in the cart, the quantity goes up instead of adding a duplicate row. So adding 1984 twice gives you 1984 with quantity 2, not two separate 1984 entries.

If you send a book ID that doesn't exist, you get a 400 Bad Request.

### 4. Update the quantity of a book
**URL:** `PUT /api/cart/{userId}/items/{bookId}`

This changes how many copies of a book are in the cart. Send the new quantity in the body:
```json
{ "quantity": 3 }
```

If you set the quantity to 0, the item is removed from the cart automatically.

If the book isn't in the cart, you get a 404.

### 5. Remove a book from the cart
**URL:** `DELETE /api/cart/{userId}/items/{bookId}`

This deletes a book from the cart. No body needed. Just the URL with the user ID and book ID.

If the book isn't in the cart, you get a 404.

## Status Codes

| Code | What it means |
|------|---------------|
| 200 | It worked. You get back the updated cart. |
| 400 | You sent a bad request, usually a book ID that doesn't exist. |
| 404 | The cart or book wasn't found. |
| 500 | Something broke on the server. Check the terminal. |

## How to Run It Locally

First, make sure MySQL is running and your `bookstore_db` database exists with the seed data loaded.

Next, open `src/main/resources/application.properties` and put your MySQL password in there.

Then, in the terminal, run:
```
.\mvnw.cmd spring-boot:run
```

After that, open Postman or a browser and hit `http://localhost:8080/api/cart/1`. If you see John's cart with two books, everything is working.

## Where the Code Lives

```
src/main/java/com/cen4010/cybersecurity_bookstore/
├── controllers/
│   └── ShoppingCartController.java
├── models/
│   ├── ShoppingCart.java
│   ├── CartItem.java
│   └── Book.java (shared)
└── repositories/
    ├── ShoppingCartRepository.java
    ├── CartItemRepository.java
    └── BookRepository.java (shared)
```

The controller has all 5 endpoints. The models map to the database tables. The repositories handle the database queries (Spring generates most of them automatically).

That's the whole feature.
