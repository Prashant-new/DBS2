package de.hsh.dbs2.imdb.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection conn = null;

	public static Connection getConnection() {
		return conn;
	}

	public static void open() throws SQLException, IOException {
		String uri = Configuration.getDbUri();
		String username = Configuration.getDbUsername();
		String password = Configuration.getDbPassword();

		if (conn != null) {
			conn.close();
			conn = null;
		}

		conn = DriverManager.getConnection(uri, username, password);
		conn.setAutoCommit(false);
		System.out.println("Connect durchgefuehrt ....");
	}
}
