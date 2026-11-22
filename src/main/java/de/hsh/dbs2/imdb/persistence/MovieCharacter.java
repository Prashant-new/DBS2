package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieCharacter {
	private Integer movcharId;
	private final int movieId;
	private final int playerId;
	private final String character;
	private final String alias;
	private final Integer pos;
	
	public MovieCharacter(Integer movcharId, int movieId, int playerId, String character, String alias, int pos) {
		this.movcharId = movcharId;
		this.movieId = movieId;
		this.playerId = playerId;
		this.character = character;
		this.alias = alias;
		this.pos = pos;
	}
	
	private MovieCharacter(ResultSet rs) throws SQLException {
		this(rs.getInt("movcharid"), rs.getInt("movieid"), rs.getInt("playerid"), rs.getString("character"), rs.getString("alias"), rs.getInt("pos"));
	}

	public MovieCharacter(int starWarsId, int johnId, String character, String alias, int pos) {
        this(null, starWarsId, johnId, character, alias, pos);
    }

    public String getCharacter() {
		return character;
	}

	public String getAlias() {
		return alias;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void insert() throws SQLException {
		int newId = PersistenceHelper.getId();
		String sql = "INSERT INTO movie_character" +
					"(movcharid, movieid, playerid, character, alias, pos)" +
					"VALUES (?; ?; ?; ?; ?; ?)";

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, newId);
			stmt.setInt(2, movieId);
			stmt.setInt(3,playerId);
			stmt.setString(4, character);
			stmt.setString(5, alias);
			stmt.setObject(6, pos, java.sql.Types.INTEGER);


			if (stmt.executeUpdate() != 1) {
				throw new SQLException("Could not insert character" + character);
			}
			this.movcharId = newId;
		}
	}
	
	public void delete() throws SQLException {
		if (movcharId == null) {
			throw new SQLException("Cannot delete an unsaved character: " + character);
		}

		String sql = "DELETE FROM movie_character WHERE movcharid = ?";

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movcharId);

			if (stmt.executeUpdate() != 1) {
				throw new SQLException("No movie character found for ID" + movcharId);
			}
		}
	}

	public static List<MovieCharacter> findByMovieId(int movieid) throws SQLException {
		String sql = "SELECT movcharid, movieid, playerid, character, alias, pos" +
					"FROM movie_character" +
					"WHERE movieid = ?";

		List<MovieCharacter> characters = new LinkedList<>();

		try (PreparedStatement stmt = 
				DBConnection.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, movieid);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					characters.add(new MovieCharacter(rs));
				}
			}

		}	
		return characters;		
	}
}
