package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.util.DBConnection;

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
		int newId = PersistenceHelper.getId();
		String sql = "INSERT INTO genre (genreid, genre) VALUES (?, ?)";

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, newId);
			stmt.setString(2, genre);

			if (stmt.executeUpdate() != 1) {
				throw new SQLException("Could not insert genre: " + genre);
			}

			this.genreId = newId;
		}
		
	}

	public void delete() throws SQLException {
		if (genreId == null) {
			throw new SQLException("Cannot delete an unsave genre: " + genre);
		}
		String sql = "DELETE FROM genre WHERE genreid = ?";

		try (PreparedStatement stmt =
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, genreId);

			if (stmt.executeUpdate() != 1) {
				throw new SQLException("No genre found for ID" + genreId);
			}

		}
		
	}
	/** Factory-Methoden **/
	
	public static List<Genre> findAll() throws SQLException {
		String sql = "SELECT genreid, genre FROM genre ORDER BY genre, genreid";
		List<Genre> genres = new ArrayList<>();

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery() ) {
			while (rs.next()) {
				genres.add(new Genre(rs));
			}
		}
		
		return genres;
	}
	
	public static List<Genre> findByMovieId(int movieId) throws SQLException {
		String sql = "SELECT g genreid, g.genre" +
					"FROM genre g" +
					"JOIN movie_genre mg ON mg.genreid = g.genreid" +
					"WHERE mg.movieid = ?" +
					"ORDER BY g.genre, g.genreid";
					
		List<Genre> genres = new ArrayList<>();

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movieId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					genres.add(new Genre(rs));
				}
			}

		}
		
		return genres;
	}

	public static Genre findByGenre(String genre) throws SQLException, DoesNotExistException {
		String sql = "SELECT genreid, genre FROM genre WHERE genre = ?";

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {

			stmt.setString(1, genre);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Genre(rs);
				}
			}


		}
		throw new DoesNotExistException("Genre not Found: " + genre);
		
	}

}
