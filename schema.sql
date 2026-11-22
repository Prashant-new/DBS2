DROP TABLE IF EXISTS movie_genre;
DROP TABLE IF EXISTS movie_character;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS person;
DROP SEQUENCE IF EXISTS dbs2_sequence;

CREATE SEQUENCE dbs2_sequence START WITH 1;

CREATE TABLE genre (
    genreid INTEGER PRIMARY KEY,
    genre VARCHAR(255) NOT NULL
);

CREATE TABLE person (
    personid INTEGER PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE movie (
    movieid INTEGER PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE movie_character (
    movcharid INTEGER PRIMARY KEY,
    movieid INTEGER NOT NULL,
    playerid INTEGER NOT NULL,
    character VARCHAR(255) NOT NULL,
    alias VARCHAR(255) NOT NULL,
    pos INTEGER NOT NULL,
    FOREIGN KEY(movieid) REFERENCES movie(movieid),
    FOREIGN KEY(playerid) REFERENCES person(personid)
);

CREATE TABLE movie_genre (
    movieid INTEGER NOT NULL,
    genreid INTEGER NOT NULL,
    PRIMARY KEY (movieid, genreid),
    FOREIGN KEY(movieid) REFERENCES movie(movieid),
    FOREIGN KEY(genreid) REFERENCES genre(genreid)
);
