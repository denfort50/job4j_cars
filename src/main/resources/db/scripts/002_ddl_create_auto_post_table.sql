CREATE TABLE IF NOT EXISTS auto_post (
    id SERIAL PRIMARY KEY,
    text VARCHAR NOT NULL UNIQUE,
    created TIMESTAMP NOT NULL,
    auto_user_id INT NOT NULL REFERENCES auto_user(id)
);