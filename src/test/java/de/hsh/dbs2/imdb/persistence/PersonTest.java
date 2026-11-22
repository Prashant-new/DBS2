package de.hsh.dbs2.imdb.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.hsh.dbs2.imdb.util.Configuration;
import de.hsh.dbs2.imdb.util.DBConnection;

public class PersonTest {

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
	public void testPerson() throws Exception {
        // wir legen zwei Personen an und speichern sie in der Datenbank
		Person john = new Person("John Doe");
        john.insert();
        Person jane = new Person("Jane Smith");
        jane.insert();
        int johnId = john.getPersonId();
        int janeId = jane.getPersonId();
        // wir lesen die Personen wieder aus der Datenbank aus und pruefen, ob sie korrekt gespeichert wurden
        john = Person.findById(johnId);
        assertEquals("John Doe", john.getName());
        jane = Person.findById(janeId);
        assertEquals("Jane Smith", jane.getName());
        john = Person.findByName("John Doe");
        assertEquals(johnId, john.getPersonId());
        jane = Person.findByName("Jane Smith");
        assertEquals(janeId, jane.getPersonId());
        // wir lesen noch mal die Liste aller Personen aus, die zu einem Teilstring passen und pruefen, ob die richtige Anzahl gefunden wurde
        List<Person> allPersonsWithJohn = Person.findByNamePart("john");
        assertTrue(allPersonsWithJohn.size() == 1);
        List<Person> allPersonsWithJ = Person.findByNamePart("j");
        assertTrue(allPersonsWithJ.size() == 2);
        // wir loeschen die beiden Personen wieder aus der Datenbank und pruefen, ob sie wirklich geloescht wurden
        john.delete();
        jane.delete();
        assertThrows(DoesNotExistException.class, () -> Person.findById(johnId));
        assertThrows(DoesNotExistException.class, () -> Person.findById(janeId));
        assertThrows(DoesNotExistException.class, () -> Person.findByName("John Doe"));
        assertThrows(DoesNotExistException.class, () -> Person.findByName("Jane Smith"));
	}

}
