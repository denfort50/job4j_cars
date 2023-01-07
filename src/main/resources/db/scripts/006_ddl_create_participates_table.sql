CREATE TABLE IF NOT EXISTS participates (
    id SERIAL PRIMARY KEY,
    post_id INT NOT NULL REFERENCES posts(id),
    user_id INT NOT NULL REFERENCES users(id)
);