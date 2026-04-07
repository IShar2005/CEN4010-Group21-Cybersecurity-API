Alessandro Squadritto
Group 21
My feature has 4 main endpoints:
- filter by genre: retrieving books by specific genre and then sorted alphabetically
- top sellers: get the top 10 bestselling books
- filter by rating: find books with minimum average rating
- apply discount: update pricing for books by publisher

the base URL for my API endpoints is = http://localhost:8080/api/books

1. GET all books
Endpoint: GET /api/books
Response: 200 OK
It would then return all the books in the database

2. GET books by genre
Endpoint: GET /api/books/genre/{genre}
It returns all the books matching the genre that was picked and it's then sorted alphabetically by title
in the genre parameter add the name of a genre: Fantasy, Science Fiction
Response 200 OK 
Error responses
400 Bad Request - Empty or not valid genre
404 Not found - No books found for genre

3. GET top sellers
Endpoint: GET /api/books/top-sellers
It returns the top 10 bestselling books, sorted by copies sold in descending order
Response: 200 OK
Error responses
404 Not found - empty database

4. GET books by rating
Endpoint: GET /api/books/rating/{minRating}
It returns all the books with the average rating being greater than or equal to the specified minimum, it's then sorted by rating (highest first)
Parameters: minRating - Minimum rating value (0.0 - 5.0)
Response: 200 OK
Error Responses
400 Bad Request: invalid rating range
404 Not Found - No books match the criteria

5. PUT apply discount by publisher
endpoint: PUT /api/books/discount
It applies a percentage discount to all books by a specific publisher. 
Parameter: publisher - publisher name & discount - discount percentage (0-100)
Success: 200 OK
Error responses: 
400 Bad Request: Missing publisher parameter or invalid discount range
404 Not Found: Publisher not found

Sprint 2: Setup the database and the initial Book entity
Sprint 3: Genre filter and top sellers endpoints
Sprint 4: Rating filter and Publisher discount endpoints
Sprint 5: Documentation and smoke testing