package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hsh.dbs2.imdb.util.DBConnection;


public class Movie {
	private Integer movieId;
	private String title;
	private int year;
	private String type;
	
	public Movie(Integer movieId, String title, int year, String type) {
		this.movieId = movieId;
		this.title = title;
		this.year = year;
		this.type = type;
	}

	public Movie(String title, int year, String type) {
		this(null, title, year, type);
	}

	private Movie(ResultSet rs) throws SQLException {
		this(rs.getInt("movieid"), rs.getString("title"), rs.getInt("year"), rs.getString("type"));
	}
	
	public Integer getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;	
	}

    public void setType(String type) {
		this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }
	
	public void insert() throws SQLException {
		/* TODO */
		int newId = PersistenceHelper.getId();
		String sql = "INSERT INTO movie (movieid, title, year) VALUES (?, ?, ?, ?)";

		try(PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1, newId);
			stmt.setString(2, getTitle());
			stmt.setInt(3, getYear());
			stmt.setString(4, getType());

			if(stmt.executeUpdate() != 1){
				throw new SQLException("Could not insert movie: " + title); 
			}
		}

		this.movieId = newId;

	}
	
	public void update() throws SQLException {
		/* TODO */
		if (movieId == null){
			throw new SQLException("Cannot update an unsaved movie");
		}

		String sql = "Update movie SET title = ?, year = ?, type = ? WHERE movieid = ?";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setString(1, title);
			stmt.setInt(2, year);
			stmt.setString(3, type);
			stmt.setInt(4, movieId);

			if (stmt.executeUpdate() != 1){
				throw new SQLException("No movie found for id" + movieId);
			}
		}
	}
	
	public void delete() throws SQLException {
		/* TODO */
		if (movieId == null) {
			throw new SQLException("Cannot delete an unsaved movie");
		}

		String sql = "DELETE FROM movie WHERE movieid = ?";

		try(PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1, movieId);

			if(stmt.executeUpdate() != 1){
				throw new SQLException("No movie found for ID" + movieId);
			}
		}
	}	

	public static List<Movie> findByTitle(String title) throws SQLException {
		/* TODO */
		String sql = """
						SELECT movieid, title, year, type
						FROM movie
						WHERE title = ?
						ORDER BY movieid
						""";
		List<Movie> movies = new ArrayList<>();

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setString(1, title);

			try (ResultSet rs = stmt.executeQuery()){
				while(rs.next()){
					movies.add(new Movie(rs));
				}
			}
		}

		return movies;
	}

	public static Movie findById(int movieId) throws SQLException, DoesNotExistException {
		/* TODO	*/
		String sql = "SELECT movieid, title, year, type" +
					"FROM movie" + 
					"WHERE movieid = ?";
			
		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1, movieId);
			
			try(ResultSet rs = stmt.executeQuery()){
				if (rs.next()){
					return new Movie(rs);
				}
			}
		}

		throw new DoesNotExistException("Movie not found for the id " + movieId);
	}

}
