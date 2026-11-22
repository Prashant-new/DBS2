package de.hsh.dbs2.imdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


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
	}
	
	public void update() throws SQLException {
		/* TODO */
	}
	
	public void delete() throws SQLException {
		/* TODO */
	}

	public static List<Movie> findByTitle(String title) throws SQLException {
		/* TODO */
		return null;
	}

	public static Movie findById(int movieId) throws SQLException, DoesNotExistException {
		/* TODO	*/
		return null;
	}

}
