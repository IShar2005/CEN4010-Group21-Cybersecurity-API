// ============================================
// Geek Text Bookstore - Profile Management

const express = require('express');
const router = express.Router();
const pool = require('./Db'); // PostgreSQL connection pool

// ─────────────────────────────────────────────
// POST /api/users
// Create a user with username, password, and
// optional fields (name, email, home address)
// ─────────────────────────────────────────────
router.post('/users', async (req, res) => {
    const { username, password, first_name, last_name, email_address, home_address } = req.body;

    if (!username || !password) {
        return res.status(400).json({ error: 'username and password are required.' });
    }

    try {
        await pool.query(
            `INSERT INTO users (username, password, first_name, last_name, email_address, home_address)
             VALUES ($1, $2, $3, $4, $5, $6)`,
            [username, password, first_name || null, last_name || null, email_address || null, home_address || null]
        );
        return res.status(201).send(); // 201 Created, no response body per spec
    } catch (err) {
        if (err.code === '23505') { // Unique violation (username or email already exists)
            return res.status(409).json({ error: 'Username or email address already exists.' });
        }
        console.error(err);
        return res.status(500).json({ error: 'Internal server error.' });
    }
});

// ─────────────────────────────────────────────
// GET /api/users/:username
// Retrieve a user object and its fields
// by their username
// ─────────────────────────────────────────────
router.get('/users/:username', async (req, res) => {
    const { username } = req.params;

    try {
        const result = await pool.query(
            `SELECT username, first_name, last_name, email_address, home_address, created_at
             FROM users
             WHERE username = $1`,
            [username]
        );

        if (result.rows.length === 0) {
            return res.status(404).json({ error: 'User not found.' });
        }

        return res.status(200).json(result.rows[0]);
    } catch (err) {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error.' });
    }
});

// ─────────────────────────────────────────────
// PATCH /api/users/:username
// Update a user's fields (except email)
// The username is used as the key lookup value
// ─────────────────────────────────────────────
router.patch('/users/:username', async (req, res) => {
    const { username } = req.params;
    const { password, first_name, last_name, home_address } = req.body;

    // Block updates to email_address per project spec
    if (req.body.email_address !== undefined) {
        return res.status(400).json({ error: 'email_address cannot be updated.' });
    }

    // Build dynamic SET clause based on provided fields
    const fields = [];
    const values = [];
    let idx = 1;

    if (password !== undefined)     { fields.push(`password = $${idx++}`);     values.push(password); }
    if (first_name !== undefined)   { fields.push(`first_name = $${idx++}`);   values.push(first_name); }
    if (last_name !== undefined)    { fields.push(`last_name = $${idx++}`);    values.push(last_name); }
    if (home_address !== undefined) { fields.push(`home_address = $${idx++}`); values.push(home_address); }

    if (fields.length === 0) {
        return res.status(400).json({ error: 'No valid fields provided to update.' });
    }

    values.push(username); // Final param for WHERE clause

    try {
        const result = await pool.query(
            `UPDATE users SET ${fields.join(', ')} WHERE username = $${idx}`,
            values
        );

        if (result.rowCount === 0) {
            return res.status(404).json({ error: 'User not found.' });
        }

        return res.status(204).send(); // 204 No Content per spec
    } catch (err) {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error.' });
    }
});

// ─────────────────────────────────────────────
// POST /api/users/:username/credit-cards
// Create a credit card that belongs to a user
// ─────────────────────────────────────────────
router.post('/users/:username/credit-cards', async (req, res) => {
    const { username } = req.params;
    const { card_number, card_holder, expiration_date, cvv } = req.body;

    if (!card_number) {
        return res.status(400).json({ error: 'card_number is required.' });
    }

    try {
        // Verify the user exists before creating a card
        const userCheck = await pool.query(
            'SELECT username FROM users WHERE username = $1',
            [username]
        );

        if (userCheck.rows.length === 0) {
            return res.status(404).json({ error: 'User not found.' });
        }

        await pool.query(
            `INSERT INTO credit_cards (username, card_number, card_holder, expiration_date, cvv)
             VALUES ($1, $2, $3, $4, $5)`,
            [username, card_number, card_holder || null, expiration_date || null, cvv || null]
        );

        return res.status(201).send(); // 201 Created, no response body per spec
    } catch (err) {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error.' });
    }
});

module.exports = router;