CREATE TABLE IF NOT EXISTS posts(
    id SERIAL PRIMARY KEY,
    price INT NOT NULL,
    text VARCHAR NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    created TIMESTAMP NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    car_id INT REFERENCES cars(id),
    photo BYTEA
);