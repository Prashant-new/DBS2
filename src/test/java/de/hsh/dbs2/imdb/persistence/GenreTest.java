package de.hsh.dbs2.imdb.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.hsh.dbs2.imdb.util.Configuration;
import de.hsh.dbs2.imdb.util.DBConnection;

public class GenreTest {

	@BeforeAll
	public static void openConnection() throws Exception {
        Configuration.read();
		DBConnection.open();
	}

    @AfterEach 
    public void teardown() throws Exception {
        DBConnection.getConnection().rollback();
    }

    @AfterAll 
    public static void closeConnection() throws Exception {
        DBConnection.getConnection().close();
    }

	@Test
	public void testGenre() throws Exception {
        // wir legen zwei Genres an und speichern sie in der Datenbank
		Genre action = new Genre("Action");
        action.insert();
        Genre comedy = new Genre("Comedy");
        comedy.insert();
        int actionId = action.getGenreId();
        int comedyId = comedy.getGenreId();
        // wir lesen die Genres wieder aus der Datenbank aus und pruefen, ob sie korrekt gespeichert wurden
        action = Genre.findByGenre("Action");
        assertEquals("Action", action.getGenre());
        assertEquals(actionId, action.getGenreId());
        comedy = Genre.findByGenre("Comedy");
        assertEquals("Comedy", comedy.getGenre());
        assertEquals(comedyId, comedy.getGenreId());
        // wir lesen noch mal die Liste aller Genres aus der Datenbank aus und pruefen, ob die beiden Genres enthalten sind
        List<Genre> allGenres = Genre.findAll();
        assertTrue(allGenres.size() == 2);
        // wir loeschen die beiden Genres wieder aus der Datenbank und pruefen, ob sie wirklich geloescht wurden
        action.delete();
        comedy.delete();
        assertThrows(DoesNotExistException.class, () -> Genre.findByGenre("Action"));
        assertThrows(DoesNotExistException.class, () -> Genre.findByGenre("Comedy"));
	}

}
