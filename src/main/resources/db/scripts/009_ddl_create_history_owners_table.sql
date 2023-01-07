CREATE TABLE IF NOT EXISTS history_owners(
    id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES drivers(id),
    car_id INT NOT NULL REFERENCES cars(id),
    startAt TIMESTAMP NOT NULL,
    endAt TIMESTAMP NOT NULL
);