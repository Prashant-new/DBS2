package de.hsh.dbs2.imdb.persistence;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hsh.dbs2.imdb.util.Configuration;
import de.hsh.dbs2.imdb.util.DBConnection;

public class MovieCharacterTest {
    
	@BeforeAll
	public static void openConnection() throws Exception {
        Configuration.read();
		DBConnection.open();
	}

    int starWarsId, e9Id, johnId, janeId;

    @BeforeEach 
    public void setup() throws Exception {
        // fuer den Test von MovieCharacter legen wir zwei Movies und zwei Personen an
        Movie starWars = new Movie("Star Wars", 1977, "C");
        starWars.insert();
        starWarsId = starWars.getMovieId();
        Movie e9 = new Movie("Star Wars Episode 9", 2018, "c");
        e9.insert();
        e9Id = e9.getMovieId();

        Person john = new Person("John Doe");
        john.insert();
        johnId = john.getPersonId();
        Person jane = new Person("Jane Smith");
        jane.insert();
        janeId = jane.getPersonId();
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
	public void testMovieCharacter() throws Exception {
        MovieCharacter starWarsJohn = new MovieCharacter(starWarsId, johnId, "Luke Skywalker", "", 1);
        starWarsJohn.insert();
        MovieCharacter starWarsJane = new MovieCharacter(starWarsId, janeId, "Leia Organa", "", 2);
        starWarsJane.insert();  


        List<MovieCharacter> characters = MovieCharacter.findByMovieId(starWarsId);
        assertEquals(2, characters.size());
        assertEquals("Luke Skywalker", characters.get(0).getCharacter());
        assertEquals("Leia Organa", characters.get(1).getCharacter());  

        characters.get(0).delete();
        characters = MovieCharacter.findByMovieId(starWarsId);
        assertEquals(1, characters.size());
        assertEquals("Leia Organa", characters.get(0).getCharacter());
        
        characters.get(0).delete();
        characters = MovieCharacter.findByMovieId(starWarsId);
        assertEquals(0, characters.size()); 
	}

}
