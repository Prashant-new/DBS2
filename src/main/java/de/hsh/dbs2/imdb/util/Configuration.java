package de.hsh.dbs2.imdb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static String dbUri;
    private static String dbUsername;
    private static String dbPassword;
    private static double guiScale;

   	public static void read() {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("gui.properties")) {
			// Eigenschaften laden
			props.load(fis);

			dbUri = props.getProperty("db.uri");
			dbUsername = props.getProperty("db.username");
			dbPassword = props.getProperty("db.password");
			guiScale = Double.parseDouble(props.getProperty("gui.scale"));
		} catch (IOException e) {
            System.err.println("Error reading gui.properties:");
            e.printStackTrace();
            System.err.println("You need to copy gui.properties.template to gui.properties and fill in the missing values.");
        }
    }

    public static String getDbUri() {
        return dbUri;
    }

    public static String getDbUsername() {
        return dbUsername;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static double getGuiScale() {
        return guiScale;
    }

}
