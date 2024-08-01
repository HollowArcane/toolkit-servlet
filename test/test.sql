DROP DATABASE sample;
CREATE DATABASE sample;

\c sample

DROP TABLE writer;
CREATE TABLE writer(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender INT NOT NULL
);

INSERT INTO writer(first_name, last_name, gender) VALUES
('John', 'Doe', 1),
('Jane', 'Smith', 0),
('Patrick', 'William', 1),
('Christel', 'Anderson', 1);

DROP TABLE publisher;
CREATE TABLE publisher(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    foundation_date DATE NOT NULL
);

INSERT INTO publisher(name, foundation_date) VALUES
('Publisher 1', '2024-01-01'),
('Publisher 2', '2024-02-01'),
('Publisher 3', '2024-03-01'),
('Publisher 4', '2024-04-01');

SELECT * FROM publisher;

CREATE TABLE book(
    id INT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,

)