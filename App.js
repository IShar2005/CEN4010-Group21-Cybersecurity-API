
const express = require('express');
const app = express();

app.use(express.json());

// Profile Management routes (Feature 2)
const profileRoutes = require('./profileRoutes');
app.use('/api', profileRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Geek Text API running on port ${PORT}`);
});

module.exports = app;