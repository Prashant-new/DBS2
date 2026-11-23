package de.hsh.dbs2.imdb.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.imdb.logic.dto.MovieDTO;
import de.hsh.dbs2.imdb.persistence.Genre;
import de.hsh.dbs2.imdb.persistence.Movie;
import de.hsh.dbs2.imdb.persistence.MovieCharacter;
import de.hsh.dbs2.imdb.persistence.MovieGenre;
import de.hsh.dbs2.imdb.persistence.Person;
import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieManager {

	/**
	 * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
	 * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
	 * Der Suchstring soll ohne Ruecksicht auf Gross-/Kleinschreibung verarbeitet werden.
	 * @param search Suchstring.
	 * @return Liste aller passenden Filme als MovieDTO
	 * @throws Exception Beschreibt evtl. aufgetretenen Fehler
	 */
	public List<MovieDTO> getMovieList(String search) throws Exception {
		Connection connection = DBConnection.getConnection();
		if (connection == null) {
			throw new IllegalStateException("Could not search for movies. Open the database connection first.");
		}
		synchronized (connection) {
			try {
				List<MovieDTO> movies = new ArrayList<>();
				for (Movie movie : Movie.findByTitle(search)) {
					int movieId = movie.getMovieId();
					MovieDTO dto = new MovieDTO();
					dto.setId(movieId);
					dto.setTitle(movie.getTitle());
					dto.setYear(movie.getYear());
					dto.setType(movie.getType());
					for (Genre genre : Genre.findByMovieId(movieId)) {
						dto.addGenre(genre.getGenre());
					}
					for (MovieCharacter character : MovieCharacter.findByMovieId(movieId)) {
						CharacterDTO characterDTO = new CharacterDTO();
						characterDTO.setCharacter(character.getCharacter());
						characterDTO.setAlias(character.getAlias());
						characterDTO.setPlayer(Person.findById(character.getPlayerId()).getName());
						dto.addCharacter(characterDTO);
					}
					movies.add(dto);
				}
				connection.commit();
				return movies;
			} catch (Exception cause) {
				try {
					connection.rollback();
				} catch (SQLException rollbackError) {
					cause.addSuppressed(rollbackError);
				}
				throw new Exception("Could not search for movies: " + cause.getMessage(), cause);
			}
		}
	}

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movieDTO Film-Objekt mit Genres und Charakteren.
	 * @throws Exception Beschreibt evtl. aufgetretenen Fehler
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		Connection connection = DBConnection.getConnection();
		if (connection == null) {
			throw new IllegalStateException("Could not save movie " + movieDTO.getTitle()
					+ ". Open the database connection first.");
		}
		synchronized (connection) {
			try {
				Movie movie = new Movie(movieDTO.getId(), movieDTO.getTitle(),
						movieDTO.getYear(), movieDTO.getType());
				if (movieDTO.getId() == null) {
					movie.insert();
				} else {
					movie.update();
					for (MovieCharacter character : MovieCharacter.findByMovieId(movie.getMovieId())) {
						character.delete();
					}
					for (MovieGenre genre : MovieGenre.findByMovieId(movie.getMovieId())) {
						genre.delete();
					}
				}

				int savedId = movie.getMovieId();
				for (String name : movieDTO.getGenres()) {
					Genre genre = Genre.findByGenre(name);
					new MovieGenre(savedId, genre.getGenreId()).insert();
				}

				int position = 1;
				for (CharacterDTO character : movieDTO.getCharacters()) {
					Person player = Person.findByName(character.getPlayer());
					new MovieCharacter(savedId, player.getPersonId(), character.getCharacter(),
							character.getAlias(), position).insert();
					position++;
				}
				connection.commit();
				movieDTO.setId(savedId);
			} catch (Exception cause) {
				try {
					connection.rollback();
				} catch (SQLException rollbackError) {
					cause.addSuppressed(rollbackError);
				}
				throw new Exception("Could not save movie " + movieDTO.getTitle()
						+ ": " + cause.getMessage(), cause);
			}
		}
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movieId id des zu löschenden Films
	 * @throws Exception Beschreibt evtl. aufgetretenen Fehler
	 */
	public void deleteMovie(int movieId) throws Exception {
		Connection connection = DBConnection.getConnection();
		if (connection == null) {
			throw new IllegalStateException("Could not delete movie " + movieId
					+ ". Open the database connection first.");
		}
		synchronized (connection) {
			try {
				Movie movie = Movie.findById(movieId);
				for (MovieCharacter character : MovieCharacter.findByMovieId(movieId)) {
					character.delete();
				}
				for (MovieGenre genre : MovieGenre.findByMovieId(movieId)) {
					genre.delete();
				}
				movie.delete();
				connection.commit();
			} catch (Exception cause) {
				try {
					connection.rollback();
				} catch (SQLException rollbackError) {
					cause.addSuppressed(rollbackError);
				}
				throw new Exception("Could not delete movie " + movieId
						+ ": " + cause.getMessage(), cause);
			}
		}
	}

	/**
	 * Ermittelt alle Daten zu einem Movie (d.h. auch Genres und Charaktere) und
	 * trägt diese Daten in einem MovieDTO-Objekt ein.
	 * @param movieId ID des Films der eingelesen wird.
	 * @return MovieDTO-Objekt mit allen Informationen zu dem Film
	 * @throws Exception Z.B. bei Datenbank-Fehlern oder falls der Movie nicht existiert.
	 */
	public MovieDTO getMovie(int movieId) throws Exception {
		Connection connection = DBConnection.getConnection();
		if (connection == null) {
			throw new IllegalStateException("Could not load movie " + movieId
					+ ". Open the database connection first.");
		}
		synchronized (connection) {
			try {
				Movie movie = Movie.findById(movieId);
				MovieDTO dto = new MovieDTO();
				dto.setId(movie.getMovieId());
				dto.setTitle(movie.getTitle());
				dto.setYear(movie.getYear());
				dto.setType(movie.getType());
				for (Genre genre : Genre.findByMovieId(movieId)) {
					dto.addGenre(genre.getGenre());
				}
				for (MovieCharacter character : MovieCharacter.findByMovieId(movieId)) {
					CharacterDTO characterDTO = new CharacterDTO();
					characterDTO.setCharacter(character.getCharacter());
					characterDTO.setAlias(character.getAlias());
					characterDTO.setPlayer(Person.findById(character.getPlayerId()).getName());
					dto.addCharacter(characterDTO);
				}
				connection.commit();
				return dto;
			} catch (Exception cause) {
				try {
					connection.rollback();
				} catch (SQLException rollbackError) {
					cause.addSuppressed(rollbackError);
				}
				throw new Exception("Could not load movie " + movieId
						+ ": " + cause.getMessage(), cause);
			}
		}
	}
}
