CREATE TABLE IF NOT EXISTS cars(
    id SERIAL PRIMARY KEY,
    brand VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    body_id INT NOT NULL REFERENCES bodies(id),
    engine_id INT NOT NULL UNIQUE REFERENCES engines(id)
);