# Shopping Cart - Smoke Test
**Isa Sharief | CEN 4010 Group 21 | Sprint 5**

## What I'm Testing

I'm running through every endpoint I built for the Shopping Cart in order. The point is to make sure they all still work together after Sprint 4. If something breaks, I want to catch it now before final submission.

## Before You Start

Make sure MySQL is running. The app needs to be running too. Start it with:
```
.\mvnw.cmd spring-boot:run
```

Also, reset the database so the data is clean. Run this in PowerShell:
```
mysql -u root -p -e "DROP DATABASE bookstore_db; CREATE DATABASE bookstore_db;"
Get-Content sql\bookstore_schema.sql | mysql -u root -p bookstore_db
```

Then restart the app. Now you're ready to test.

---

## Test 1: Get John's cart
**Method:** GET
**URL:** `http://localhost:8080/api/cart/1`

I'm checking that John's cart shows up with his original items. He should have 1984 with quantity 1 and The Hobbit with quantity 2.

**Expected:** 200 OK with both books showing
**Actual:** _____
**Pass/Fail:** _____

---

## Test 2: Get a cart that doesn't exist
**Method:** GET
**URL:** `http://localhost:8080/api/cart/999`

This is the error case. User 999 doesn't exist, so there should be no cart.

**Expected:** 404 Not Found
**Actual:** _____
**Pass/Fail:** _____

---

## Test 3: Get all carts
**Method:** GET
**URL:** `http://localhost:8080/api/cart`

Just making sure the all-carts endpoint still returns everything.

**Expected:** 200 OK with all carts in the database
**Actual:** _____
**Pass/Fail:** _____

---

## Test 4: Add a new book to John's cart
**Method:** POST
**URL:** `http://localhost:8080/api/cart/1/items`
**Body:**
```json
{ "bookId": 3, "quantity": 1 }
```

Now I'm adding The Great Gatsby to John's cart. He should now have three books instead of two.

**Expected:** 200 OK with all 3 books in the cart
**Actual:** _____
**Pass/Fail:** _____

---

## Test 5: Add a book that's already in the cart
**Method:** POST
**URL:** `http://localhost:8080/api/cart/1/items`
**Body:**
```json
{ "bookId": 1, "quantity": 2 }
```

This is the important one. 1984 is already in John's cart. Instead of making a duplicate row, the quantity should just go up.

**Expected:** 1984 quantity goes from 1 to 3 (no duplicate)
**Actual:** _____
**Pass/Fail:** _____

---

## Test 6: Try to add a book that doesn't exist
**Method:** POST
**URL:** `http://localhost:8080/api/cart/1/items`
**Body:**
```json
{ "bookId": 999, "quantity": 1 }
```

Another error case. Book 999 doesn't exist, so this should fail.

**Expected:** 400 Bad Request
**Actual:** _____
**Pass/Fail:** _____

---

## Test 7: Update the quantity of 1984
**Method:** PUT
**URL:** `http://localhost:8080/api/cart/1/items/1`
**Body:**
```json
{ "quantity": 5 }
```

Changing the quantity of 1984 to 5.

**Expected:** 200 OK with 1984 now showing quantity 5
**Actual:** _____
**Pass/Fail:** _____

---

## Test 8: Set quantity to 0 (should remove the item)
**Method:** PUT
**URL:** `http://localhost:8080/api/cart/1/items/3`
**Body:**
```json
{ "quantity": 0 }
```

This is a special case. Setting quantity to 0 should remove the book entirely instead of leaving a row with 0 in it.

**Expected:** The Great Gatsby is gone from the cart
**Actual:** _____
**Pass/Fail:** _____

---

## Test 9: Delete a book from the cart
**Method:** DELETE
**URL:** `http://localhost:8080/api/cart/1/items/5`

Removing The Hobbit using the DELETE endpoint instead of setting quantity to 0.

**Expected:** The Hobbit is gone, only 1984 remains
**Actual:** _____
**Pass/Fail:** _____

---

## Test 10: Try to delete a book that's not in the cart
**Method:** DELETE
**URL:** `http://localhost:8080/api/cart/1/items/999`

Final error case. Can't delete what isn't there.

**Expected:** 404 Not Found
**Actual:** _____
**Pass/Fail:** _____

---

## Results Summary

| Test | What I Tested | Result |
|------|---------------|--------|
| 1 | Get cart by user ID | |
| 2 | Get cart for non-existent user | |
| 3 | Get all carts | |
| 4 | Add a new book | |
| 5 | Add an existing book (quantity up) | |
| 6 | Add invalid book ID | |
| 7 | Update quantity | |
| 8 | Set quantity to 0 (remove) | |
| 9 | Delete a book | |
| 10 | Delete book not in cart | |

**Total tests:** 10
**Passed:** ___
**Failed:** ___
