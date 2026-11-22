package de.hsh.dbs2.imdb.persistence;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.hsh.dbs2.imdb.util.Configuration;
import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieTest {
    
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
	public void testMovie() throws Exception {
        // wir legen zwei Movies an und speichern sie in der Datenbank
		Movie starWars = new Movie("Star Wars", 1977, "C");
        starWars.insert();
        int starWarsId = starWars.getMovieId();
        Movie e9 = new Movie("Star Wars Episode 9", 2018, "c");
        e9.insert();
        int e9Id = e9.getMovieId();
        // wir lesen die Movies wieder aus der Datenbank aus und pruefen, ob sie korrekt gespeichert wurden
        starWars = Movie.findById(starWarsId);
        assertEquals("Star Wars", starWars.getTitle());
        assertEquals(1977, starWars.getYear());
        assertEquals("C", starWars.getType());
        e9 = Movie.findById(e9Id);
        assertEquals("Star Wars Episode 9", e9.getTitle());
        assertEquals(2018, e9.getYear());
        assertEquals("c", e9.getType());

        // wir aendern die Daten von E9 und speichern sie in der Datenbank
        e9.setTitle("Star Wars: Episode IX");
        e9.setYear(2019);
        e9.setType("C");
        e9.update();
        // wir lesen den Movie wieder aus der Datenbank aus und pruefen, ob alles korrekt gespeichert wurde
        e9 = Movie.findById(e9Id);
        assertEquals("Star Wars: Episode IX", e9.getTitle());
        assertEquals(2019, e9.getYear());
        assertEquals("C", e9.getType());

        // wir lesen noch mal die Liste aller Movies aus der Datenbank aus und pruefen, ob die beiden Movies enthalten sind
        List<Movie> movies = Movie.findByTitle("star wars");
        assertTrue(movies.size() == 2);
        movies = Movie.findByTitle("episode ix");
        assertTrue(movies.size() == 1);
        // wir loeschen die beiden Movies wieder aus der Datenbank und pruefen, ob sie wirklich geloescht wurden
        starWars.delete();
        assertThrows(DoesNotExistException.class, () -> Movie.findById(starWarsId));
        e9.delete();
        assertThrows(DoesNotExistException.class, () -> Movie.findById(e9Id));
	}

}
