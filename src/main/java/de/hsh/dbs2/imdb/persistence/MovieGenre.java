package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieGenre {
	
	private final int movieId;
	private final int genreId;

	public MovieGenre(int movieId, int genreId) {
		this.movieId = movieId;
		this.genreId = genreId;
	}

	private MovieGenre(ResultSet rs) throws SQLException {
		this(rs.getInt("movieid"), rs.getInt("genreid"));
	}

	public void insert() throws SQLException {
		/* TODO */
	}

	public void delete() throws SQLException {
		/* TODO */
	}

	public static List<MovieGenre> findByMovieId(int movieId) throws SQLException {
		/* TODO */
		return null;
	}

}
