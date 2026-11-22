package de.hsh.dbs2.imdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Genre {

	private Integer genreId;
	private final String genre;
	
	private Genre(int genreId, String genre) {
		this.genreId = genreId;
		this.genre = genre;
	}

	private Genre(ResultSet rs) throws SQLException {
		this(rs.getInt("genreid"), rs.getString("genre"));
	}

	public Genre(String genre) {
        this.genreId = null;
		this.genre = genre;
    }

    public Integer getGenreId() {
		return genreId;
	}

	public String getGenre() {
		return genre;
	}

	public void insert() throws SQLException {
		/* TODO */
	}

	public void delete() throws SQLException {
		/* TODO */
	}
	/** Factory-Methoden **/
	
	public static List<Genre> findAll() throws SQLException {
		/* TODO */
		return new ArrayList<>();
	}
	
	public static List<Genre> findByMovieId(int movieId) throws SQLException {
		/* TODO */
		return new ArrayList<>();
	}

	public static Genre findByGenre(String genre) throws SQLException, DoesNotExistException {
		/* TODO */
		return null;
	}

}
