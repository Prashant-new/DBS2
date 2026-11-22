DROP TABLE IF EXISTS movie_genre;
DROP TABLE IF EXISTS movie_character;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS person;
DROP SEQUENCE IF EXISTS dbs2_sequence;

CREATE SEQUENCE dbs2_sequnce START WITH 1;

CREATE TABLE genre (
    genre_id INTEGER PRIMARY KEY,
    genre VARCHAR(255) NOT NULL
);

CREATE TABLE person (
    person_id INTEGER PRIMARY KEY,
    person_name VARCHAR(100)
);

CREATE TABLE movie (
    movie_id INTEGER PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE movie_character (
    mov_char_id INTEGER PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    person_id INTEGER NOT NULL,
    character VARCHAR(255) NOT NULL,
    alias VARCHAR(255) NOT NULL,
    position INTEGER NOT NULL,
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY(person_id) REFERENCES person(person_id)
);

CREATE TABLE movie_genre (
    movie_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY(genre_id) REFERENCES genre(genre_id)
);