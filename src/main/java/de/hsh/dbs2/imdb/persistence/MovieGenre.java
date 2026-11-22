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
		String sql = "INSERT INTO movie_genre WHERE movieid = ? AND genreid = ?";

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movieId);
			stmt.setInt(2, genreId);

			if (stmt.executeUpdate() != 1) {
				throw new SQLException("No link found between movie " + movieId + " and genre " + genreId);

			}
		}
	}

	public void delete() throws SQLException {
		String sql = "DELETE FROM movie_genre WHERE movieid = ? AND genreid = ?";
		
		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movieId);
			stmt.setInt(2, genreId);

			if (stmt.executeUpdate() != 1) {
				throw new SQLException("No link found between movie " + movieId + " and genre " + genreId);

			}
		}
		
	}

	public static List<MovieGenre> findByMovieId(int movieId) throws SQLException {
		String sql = "SELECT movieid, genreid From movie_genre WHERE movieid = ?";
		List<MovieGenre> links = new ArrayList<>();

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movieId);

			try(ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					links.add(new MovieGenre(rs));
				}
			}
		}
		return links;
	}

}
