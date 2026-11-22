package de.hsh.dbs2.imdb.persistence;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hsh.dbs2.imdb.util.Configuration;
import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieGenreTest {
    
    public int starWarsId, e9Id, actionId, comedyId;

	@BeforeAll
	public static void openConnection() throws Exception {
        Configuration.read();
		DBConnection.open();
    }

    @BeforeEach 
    public void setup() throws Exception {
        // fuer den Test von MovieGenre legen wir zwei Genres und zwei Movies an.
        Movie starWars = new Movie("Star Wars", 1977, "C");
        starWars.insert();
        starWarsId = starWars.getMovieId();
        Movie e9 = new Movie("Star Wars Episode 9", 2018, "c");
        e9.insert();
        e9Id = e9.getMovieId();

        Genre action = new Genre("Action");
        action.insert();
        Genre comedy = new Genre("Comedy");
        comedy.insert();
        actionId = action.getGenreId();
        comedyId = comedy.getGenreId();
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
	public void testMovieGenre() throws Exception {
        MovieGenre starWarsAction = new MovieGenre(starWarsId, actionId);
        starWarsAction.insert();
        MovieGenre starWarsComedy = new MovieGenre(starWarsId, comedyId);
        starWarsComedy.insert();
        MovieGenre e9Action = new MovieGenre(e9Id, actionId);
        e9Action.insert();


        e9Action.delete();
        assertEquals(2, MovieGenre.findByMovieId(starWarsId).size());
        assertEquals(0, MovieGenre.findByMovieId(e9Id).size());
        starWarsAction.delete();
        assertEquals(1, MovieGenre.findByMovieId(starWarsId).size());
        assertEquals(0, MovieGenre.findByMovieId(e9Id).size());
        starWarsComedy.delete();
        assertEquals(0, MovieGenre.findByMovieId(starWarsId).size());
        assertEquals(0, MovieGenre.findByMovieId(e9Id).size());
	}

}
