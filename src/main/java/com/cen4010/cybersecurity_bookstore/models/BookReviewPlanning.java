package com.cen4010.cybersecurity_bookstore.models;
// Sprint 2 Planning - Book Commenting and Rating Feature
//
// This feature allows users to rate and comment on books they have purchased.
//
// Planned Tables:
//
// Rating Table
// - rating_id (Primary Key)
// - user_id (Foreign Key)
// - book_id (Foreign Key)
// - rating_value (1-5)
// - date_created
//
// Comment Table
// - comment_id (Primary Key)
// - user_id (Foreign Key)
// - book_id (Foreign Key)
// - comment_text
// - date_created
//
// Relationships:
// - One User can create many Ratings
// - One User can create many Comments
// - One Book can have many Ratings
// - One Book can have many Comments
//
// Average rating will be calculated dynamically using rating values.

public class BookReviewPlanning {
}
