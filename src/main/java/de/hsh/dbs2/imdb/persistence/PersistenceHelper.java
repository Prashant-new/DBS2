package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hsh.dbs2.imdb.util.DBConnection;

public class PersistenceHelper {

    public static int getId() throws SQLException {
		String sql = "SELECT nextval('dbs2_sequence') id";
		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			rs.next();
			return rs.getInt("id");
		}
    }
   
}
