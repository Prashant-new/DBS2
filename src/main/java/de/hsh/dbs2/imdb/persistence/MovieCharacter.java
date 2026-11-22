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
		/* TODO */
	}
	
	public void delete() throws SQLException {
		/* TODO */
	}

	public static List<MovieCharacter> findByMovieId(int movieid) throws SQLException {
		/* TODO */
		return null;		
	}
}
